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


const AmountContainer = styled.div`
  display:inline;
  
`;


const Amount = styled.div`
  float:left;
  margin-right: 8px;
  font-weight: bold;
  
`;


const Currency = styled.div`
  float:left;
  font-weight: bold;
`;

const DateDiv = styled.div`
  font-weight: bold;
  color: #06c4ff;
`;

const TimeDiv = styled.div`
  color: rgb(215, 219, 224);
  font-size: 0.8em;
`;

const InfoDiv = styled.div`
  max-width: 200px;
  text-align: center;
  font-weight: lighter;
  
`;

const DateTimeCont = styled.div`
  text-align: center;
  
`;

const CreditDebit = styled.div`
    font-size: 0.9em;
    color: #069cc9;
`;



class TransactionItem extends React.Component{
    constructor(props) {
        super(props);



        this.state = {
            transaction: this.props.transaction
        }

    }

    componentDidMount() {

    }


    render() {
        return(
            <Container>
                <DateTimeCont>
                    <DateDiv>{new Date(this.state.transaction.bookingDateTime).toLocaleDateString('de-DE')}</DateDiv>
                    <TimeDiv>{new Date(this.state.transaction.bookingDateTime).toLocaleTimeString('de-DE')}</TimeDiv>
                </DateTimeCont>
                <div>
                    <InfoDiv>{this.state.transaction.transactionInformation}</InfoDiv>
                    <CreditDebit>{this.state.transaction.creditDebitIndicator}</CreditDebit>
                </div>
                <AmountContainer>
                    <Amount>{Number(Math.ceil(this.state.transaction.amount*20- 0.5)/20).toFixed(2)}</Amount>
                    <Currency>{this.state.transaction.currency}</Currency>
                </AmountContainer>


            </Container>
        )
    }

}

export default TransactionItem;