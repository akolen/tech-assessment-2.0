package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.entity.Account;
import ch.uzh.ifi.seal.soprafs20.entity.Transaction;
import ch.uzh.ifi.seal.soprafs20.repository.AccountRepository;
import ch.uzh.ifi.seal.soprafs20.repository.TransactionRepository;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountService(@Qualifier("accountRepository") AccountRepository accountRepository, @Qualifier("transactionRepository")TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    String CLIENT_ID = "9M9CCOu9m6hTBCrZDm1Cpvv-3iL_xKCaPWHtgLPF3cA=";
    String CLIENT_SECRET = "ULb1CD8NjHO7DAtgai7ftUK3rxUzHIsB6ky1E5JYDOw=";
    String redirectURI = "https://a7d8c5a6-2a9c-4a0c-8b02-6eb248dc98ce.example.org/redirect";
    String encodedRedirectURI = "https%3A%2F%2Fa7d8c5a6-2a9c-4a0c-8b02-6eb248dc98ce.example.org%2Fredirect";
    String authorization_username = "123456789012@a7d8c5a6-2a9c-4a0c-8b02-6eb248dc98ce.example.org";

    public String getAccessToken() throws IOException {
        URL url = new URL("https://ob.sandbox.natwest.com/token");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setRequestProperty("Accept", "application/json");



        String data = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s&scope=accounts", CLIENT_ID, CLIENT_SECRET);

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());


        //read the response from InputStream
        try(BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //System.out.println(response.toString());
            http.disconnect();
            String JSONString = response.toString();
            JSONObject obj = new JSONObject(JSONString);
            String accessToken = obj.getString("access_token");
            //System.out.println(accessToken);
            return accessToken;
        }

    }


    public String postAccountRequest(String accessToken) throws IOException {
        URL url = new URL("https://ob.sandbox.natwest.com/open-banking/v3.1/aisp/account-access-consents");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", String.format("Bearer %s", accessToken));
        http.setRequestProperty("Content-Type", "application/json");
        String data = "{\n  \"Data\": {\n    \"Permissions\": [\n      \"ReadAccountsDetail\",\n      \"ReadBalances\",\n      \"ReadTransactionsCredits\",\n      \"ReadTransactionsDebits\",\n      \"ReadTransactionsDetail\"\n    ]\n  },\n  \"Risk\": {}\n}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

        //read the response from InputStream
        try(BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //System.out.println(response.toString());
            http.disconnect();
            String JSONString = response.toString();
            JSONObject obj = new JSONObject(JSONString);
            String ConsentId = obj.getJSONObject("Data").getString("ConsentId");
            //System.out.println(ConsentId);
            return ConsentId;
        }
    }


    public String getConsentApproval(String consentId) throws IOException {
        URL url = new URL(String.format("https://api.sandbox.natwest.com/authorize?client_id=%s&response_type=code id_token&scope=openid accounts&redirect_uri=%s&state=ABC&request=%s&authorization_mode=AUTO_POSTMAN&authorization_result=APPROVED&authorization_username=%s", CLIENT_ID, encodedRedirectURI, consentId, authorization_username));

        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

        //read the response from InputStream
        try(BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //System.out.println(response.toString());
            http.disconnect();
            String JSONString = response.toString();
            JSONObject obj = new JSONObject(JSONString);
            String redirectUri = obj.getString("redirectUri");
            //split redirectUri to get authorization code
            String[] parts = redirectUri.split("#code=");
            String authCode = parts[1];

            return authCode;

        }
    }

    public String exchangeCodeForAccessToken(String authorizationCode) throws IOException {
        URL url = new URL("https://ob.sandbox.natwest.com/token");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String data = String.format("client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code&code=%s", CLIENT_ID, CLIENT_SECRET, redirectURI, authorizationCode);

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        //read the response from InputStream
        try(BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //System.out.println(response.toString());
            http.disconnect();
            String JSONString = response.toString();
            JSONObject obj = new JSONObject(JSONString);
            String apiAccessToken = obj.getString("access_token");

            return apiAccessToken;

        }
    }

    public List<Account> getAccounts(String APIaccessToken) throws IOException {
        System.out.println("here");
        URL url = new URL("https://ob.sandbox.natwest.com/open-banking/v3.1/aisp/accounts");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", String.format("Bearer %s", APIaccessToken));

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        //read the response from InputStream

        try(BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            //System.out.println("accounts --> " + response.toString());
            http.disconnect();

            String JSONString = response.toString();
            JSONObject obj = new JSONObject(JSONString);
            JSONArray accountsList = obj.getJSONObject("Data").getJSONArray("Account");


            //save each account in accountRepository
            for(int num = 0; num < accountsList.length(); num++){
                JSONObject account = accountsList.getJSONObject(num);
                Account newAccount = new Account();

                String accountId = account.getString("AccountId");
                newAccount.setAccountId(accountId);
                String currency = account.getString("Currency");
                newAccount.setCurrency(currency);
                String accountType = account.getString("AccountType");
                newAccount.setAccountType(accountType);
                String accountSubType = account.getString("AccountSubType");
                newAccount.setAccountSubType(accountSubType);
                Float balance = this.getBalanceForAccount(accountId,APIaccessToken);
                newAccount.setBalance(balance);

                //System.out.println(newAccount.toString());


                newAccount = accountRepository.save(newAccount);
                accountRepository.flush();

            }

            return accountRepository.findAll();

        }

    }

    public Float getBalanceForAccount(String accountId, String APIaccessToken) throws IOException {
        URL url2 = new URL(String.format("https://ob.sandbox.natwest.com/open-banking/v3.1/aisp/accounts/%s/balances", accountId));
        HttpURLConnection http2 = (HttpURLConnection)url2.openConnection();
        http2.setRequestProperty("Authorization", String.format("Bearer %s", APIaccessToken));

        System.out.println(http2.getResponseCode() + " " + http2.getResponseMessage());

        //read the response from InputStream
        try(BufferedReader br = new BufferedReader(new InputStreamReader(http2.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //System.out.println(response.toString());
            http2.disconnect();

            String JSONString = response.toString();
            JSONObject obj = new JSONObject(JSONString);
            JSONArray balances = obj.getJSONObject("Data").getJSONArray("Balance");

            //System.out.println("balances --> " + balances);

            JSONObject accountBalanceInfo = balances.getJSONObject(0);
            //System.out.println("accountBalanceInfo -->" + accountBalanceInfo.toString());

            //returns JSON in the form {"Amount":"000", "Currency": "CHF"}
            JSONObject amountInfo = accountBalanceInfo.getJSONObject("Amount");
            //System.out.println("amountInfo -->" + amountInfo.toString());

            String amount = amountInfo.getString("Amount");
            //System.out.println("amount-->" + amount);


            for (int i=0; i<balances.length(); i++){
                JSONObject accountBalanceInfo2 = balances.getJSONObject(i);
                //System.out.println("accountBalanceInfo2 -->" + accountBalanceInfo2.toString());
            }

            return Float.parseFloat(amount);

        }

    }


    public List<Transaction> getTransactions(String accountId, String APIaccessToken) throws IOException {
        URL url = new URL(String.format("https://ob.sandbox.natwest.com/open-banking/v3.1/aisp/accounts/%s/transactions", accountId));
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", String.format("Bearer %s", APIaccessToken));

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

        //read the response from InputStream
        try(BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //System.out.println(response.toString());
            http.disconnect();

            String JSONString = response.toString();
            JSONObject obj = new JSONObject(JSONString);
            JSONArray transactions = obj.getJSONObject("Data").getJSONArray("Transaction");

            System.out.println("transactions --> " + transactions);

            for(int i=0; i<transactions.length(); i++){
                JSONObject transaction = transactions.getJSONObject(i);
                Transaction newtransaction = new Transaction();


                newtransaction.setAccountId(transaction.getString("AccountId"));
                newtransaction.setTransactionId(transaction.getString("TransactionId"));
                JSONObject amountObject = transaction.getJSONObject("Amount");
                newtransaction.setAmount(Float.parseFloat(amountObject.getString("Amount")));
                newtransaction.setCurrency(amountObject.getString("Currency"));
                newtransaction.setCreditDebitIndicator(transaction.getString("CreditDebitIndicator"));
                newtransaction.setTransactionInformation(transaction.getString("TransactionInformation"));

                String date = "2022-05-31T06:54:00.000Z";
                SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                java.util.Date dtIn = inFormat.parse(transaction.getString("BookingDateTime"));


                newtransaction.setBookingDateTime(dtIn);


                System.out.println("new transaction  " + newtransaction.toString());

                newtransaction = transactionRepository.save(newtransaction);
                transactionRepository.flush();
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return transactionRepository.findAllByAccountId(accountId);
    }
}



