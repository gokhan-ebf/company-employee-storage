//const React = require('react');
const ReactDOM = require('react-dom');
//const client = require('./client');

import React from 'react'
import { Container, Row, Col } from 'reactstrap'
import CompanyModalForm from './components/company/CompanyModalForm'
import CompanyDataTable from './components/company/CompanyDataTable'
import { CSVLink } from "react-csv"

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {companies: []};
    }

    componentDidMount() {
       this.getCompanies()
    }
// client({method: 'GET', path: '../api/v1/companies'}).done(response => {
//             this.setState({companies: response.entity});
//         });
   ///
    getCompanies(){
        fetch('../api/v1/company')
            .then(response => response.json())
            .then(companies => this.setState({companies}))
            .catch(err => console.log(err))
    }

    addCompanyToState = (company) => {
        this.setState(prevState => ({
            companies: [...prevState.companies, company]
        }))
    }

    updateState = (company) => {
        const companyIndex = this.state.companies.findIndex(data => data.id === company.id)
        const newArray = [
            // destructure all companies from beginning to the indexed company
            ...this.state.companies.slice(0, companyIndex),
            // add the updated company to the array
            company,
            // add the rest of the companies to the array from the index after the replaced company
            ...this.state.companies.slice(companyIndex + 1)
        ]
        this.setState({ companies: newArray })
    }

    deleteCompanyFromState = (id) => {
        const updatedCompanies = this.state.companies.filter(company => company.id !== id)
        this.setState({ companies: updatedCompanies })
    }
    render() {
        return (
          //  <CompanyList companies={this.state.companies}/>
            <Container className="App">
                <Row>
                    <Col>
                        <h1 style={{margin: "20px 0"}}>CRUD Database</h1>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <CompanyDataTable companies={this.state.companies} updateState={this.updateState} deleteCompanyFromState={this.deleteCompanyFromState} />
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <CSVLink
                            filename={"db.csv"}
                            color="primary"
                            style={{float: "left", marginRight: "10px"}}
                            className="btn btn-primary"
                            data={this.state.companies}>
                            Download CSV
                        </CSVLink>
                        <CompanyModalForm buttonLabel="Add Company" addCompanyToState={this.addCompanyToState}/>
                    </Col>
                </Row>
            </Container>
        )
    }
}

class CompanyList extends React.Component{
    render() {
        const companies = this.props.companies.map(company =>
            <Company key={company.id} company={company}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                </tr>
                {companies}
                </tbody>
            </table>
        )
    }
}

class Company extends React.Component{
    render() {
        console.log(this.props.company);
        return (
            <tr>
                <td>{this.props.company.id}</td>
                <td>{this.props.company.name}</td>
            </tr>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)