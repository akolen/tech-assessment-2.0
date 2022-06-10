import React from "react";
import styled from "styled-components";

const Container = styled.div`
  margin: 6px 0;
  min-width: 450px;
  padding: 10px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid #ffffff26;
`;


const BankName = styled.div`
  font-weight: bold;
  color: #06c4ff;
`;

const AccountType = styled.div`
  font-weight: lighter;
  height: 30px;
  line-height: 30px;
  text-align: center;
  display: inline-block;
`;

const TypeContainer = styled.div`
  display: grid;
  margin: 0em 1em 0em 1em;
  text-align: center
`;

const BalanceContainer = styled.div`
  display:inline;
  
`;


const Balance = styled.div`
  float:left;
  margin-right: 8px;
  font-weight: bold;
  
`;


const Currency = styled.div`
  float:left;
  font-weight: bold;
  
`;







/**
 * This is an example of a Functional and stateless component (View) in React. Functional components are not classes and thus don't handle internal state changes.
 * Conceptually, components are like JavaScript functions. They accept arbitrary inputs (called “props”) and return React elements describing what should appear on the screen.
 * They are reusable pieces, and think about each piece in isolation.
 * Functional components have to return always something. However, they don't need a "render()" method.
 * https://reactjs.org/docs/components-and-props.html
 * @FunctionalComponent
 */
class Account extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            account: this.props.account,
            amountColor: null
        }
    }

    componentDidMount() {
        console.log(this.state.account);
        console.log("this.state.amountColor before", this.state.amountColor);
        //componentDidMount() gets called for each account once
        //sets the amountColor to red for every account balance that is negative
        if(this.state.account.balance < 0 ){
            this.setState({amountColor:"rgb(227,59,59)"});
        }
    }


    render() {
        return (
            <Container>
                <BankName>{this.state.account.bankName}</BankName>
                <TypeContainer>
                    <AccountType>{this.state.account.accountType}</AccountType>
                    <AccountType>{this.state.account.accountSubType}</AccountType>
                </TypeContainer>
                <BalanceContainer>
                    <Balance style={{color: this.state.amountColor}}>{Number(Math.ceil(this.state.account.balance*20- 0.5)/20).toFixed(2)}</Balance>
                    <Currency style={{color: this.state.amountColor}}>{this.state.account.currency}</Currency>
                </BalanceContainer>
            </Container>
        );
    }


}

export default Account;
