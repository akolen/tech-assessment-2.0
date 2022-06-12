import React from 'react';
import styled from 'styled-components';
import {BaseContainer} from '../../helpers/layout';
import {api, handleError} from '../../helpers/api';
import {Spinner} from '../../views/design/Spinner';
import TransactionItem from "../../views/TransactionItem.js";

const Container = styled(BaseContainer)`
  color: white;
  text-align: center;
`;

const TransactionsList = styled.ul`
  list-style: none;
  padding-left: 0;
`;

const TransactionsContainer = styled.li`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const TotalSum = styled.div`
  font-weight: bold;
  font-size: 22px;
  
  
`;


const TotalBalance = styled.div`
  margin: 0px 8px 0px 8px;
  display: inline-block;
`;


const Currency = styled.div`
  display: inline-block;
`;


class Transactions extends React.Component{
    constructor(props) {
        super(props);

        this.state={
            accountBalance: null,
            transactions: null,
            currency: null
        }
    }



    async componentDidMount() {
        try {
            const url = window.location.href.toString().split("/")
            const accountId = url.slice(-1);


            const response = await api.get('/transactions/'+accountId);
            const response2 = await api.get('/accounts');
                // delays continuous execution of an async operation for 1 second.
            // This is just a fake async call, so that the spinner can be displayed
            // feel free to remove it :)
            await new Promise(resolve => setTimeout(resolve, 1000));

            // Get the returned accounts and update the state.
            this.setState({ transactions: response.data });
            let account;
            for(let i =0; i<response2.data.length; i++){
                if(response2.data[i].accountId == accountId){
                    account = response2.data[i];
                }
            }
            console.log("account details", account);
            this.setState({ accountBalance: account.balance });

            // This is just some data for you to see what is available.
            // Feel free to remove it.
            console.log('request to:', response.request.responseURL);
            console.log('status code:', response.status);
            console.log('status text:', response.statusText);
            console.log('requested data:', response.data);

            console.log('2 request to:', response2.request.responseURL);
            console.log('2 status code:', response2.status);
            console.log('2 status text:', response2.statusText);
            console.log('2 requested data:', response2.data);

            // See here to get more data.
            console.log(response);
            console.log(response2);

            this.setState({currency: this.state.transactions[0].currency}, ()=>{
                console.log("account currency",this.state.currency);
            })


        } catch (error) {
            alert(`Something went wrong while fetching the transactions: \n${handleError(error)}`);
        }

        //change color of total balance to red if it is negative
        if(this.state.accountBalance < 0 ){
            const balance = document.getElementById("account-balance");
            const currency = document.getElementById("account-currency");
            console.log(balance);
            balance.style.color="rgb(227,59,59)";
            currency.style.color="rgb(227,59,59)";
        }
    }

    render() {
        return(
            <Container>
                {!this.state.transactions ? (
                    <Spinner />
                ) : (
                    <div>
                        <TotalSum>
                            Kontostand:
                            <TotalBalance id={"account-balance"}>{Number((Math.ceil(this.state.accountBalance*20 - 0.5)/20)).toFixed(2)}</TotalBalance>
                            <Currency id={"account-currency"}>{this.state.currency}</Currency>
                        </TotalSum>
                        <TransactionsList>
                            {this.state.transactions.map(transaction => {
                                return (
                                    <TransactionsContainer key={transaction.transactionId}>
                                        <TransactionItem transaction={transaction}/>
                                    </TransactionsContainer>
                                );
                            })}
                        </TransactionsList>
                    </div>
                )}

            </Container>
        )
    }
}

export default Transactions;
