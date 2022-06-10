import React from 'react';
import styled from 'styled-components';
import { BaseContainer } from '../../helpers/layout';
import { api, handleError } from '../../helpers/api';
import { Spinner } from '../../views/design/Spinner';
import { Button } from '../../views/design/Button';
import { withRouter } from 'react-router-dom';
import Account from "../../views/Account";

const Container = styled(BaseContainer)`
  color: white;
  text-align: center;
`;

const AccountsList = styled.ul`
  list-style: none;
  padding-left: 0;
`;

const AccountsContainer = styled.li`
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





class Game extends React.Component {
  constructor() {
    super();
    this.state = {
      accounts: null,
      sum: null,
      currency: null
    };
  }

  logout() {
    localStorage.removeItem('token');
    this.props.history.push('/login');
  }

  async componentDidMount() {
    try {
      const response = await api.get('/accounts');
      // delays continuous execution of an async operation for 1 second.
      // This is just a fake async call, so that the spinner can be displayed
      // feel free to remove it :)
      await new Promise(resolve => setTimeout(resolve, 1000));

      // Get the returned accounts and update the state.
      this.setState({ accounts: response.data });

      // This is just some data for you to see what is available.
      // Feel free to remove it.
      console.log('request to:', response.request.responseURL);
      console.log('status code:', response.status);
      console.log('status text:', response.statusText);
      console.log('requested data:', response.data);

      // See here to get more data.
      console.log(response);

      //get sum of all balances (assuming the same currency for all accounts
      let total = 0;
      for(let i = 0; i<this.state.accounts.length; i++){
        total += this.state.accounts[i].balance;
      }
      this.setState({sum: total}, ()=>{
        console.log("totalsum balances", this.state.sum);
      });

      this.setState({currency: this.state.accounts[0].currency}, ()=>{
        console.log("account currency",this.state.currency);
      })


    } catch (error) {
      alert(`Something went wrong while fetching the accounts: \n${handleError(error)}`);
    }

    //change color of total balance to red if it is negative
    if(this.state.sum < 0 ){
      const balance = document.getElementById("total-balance");
      const currency = document.getElementById("currency");
      console.log(balance);
      balance.style.color="rgb(227,59,59)";
      currency.style.color="rgb(227,59,59)";
    }
  }

  render() {

    return (
      <Container>
        <TotalSum>
          Gesamtvermögen:
          <TotalBalance id={"total-balance"}>
            {Number((Math.ceil(this.state.sum*20 - 0.5)/20)).toFixed(2)}
          </TotalBalance>
          <Currency id={"currency"}>
            {this.state.currency}
          </Currency>
        </TotalSum>
        <p>Ihre Kontenübersicht:</p>
        {!this.state.accounts ? (
          <Spinner />
        ) : (
          <div>
            <AccountsList>
              {this.state.accounts.map(account => {
                return (
                  <AccountsContainer key={account.accountId}>
                    <Account account={account} />
                  </AccountsContainer>
                );
              })}
            </AccountsList>
            <Button
              width="100%"
              onClick={() => {
                this.logout();
              }}
            >
              Logout
            </Button>
          </div>
        )}
      </Container>
    );
  }
}

export default withRouter(Game);
