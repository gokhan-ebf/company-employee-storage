import React, { Component } from 'react'
import { Table, Button } from 'reactstrap';
import CompanyModalForm from './CompanyModalForm'

class CompanyDataTable extends Component {

    deleteCompany = id => {
        let confirmDelete = window.confirm('Delete company forever?')
        if(confirmDelete){
            fetch('../api/v1/company', {
                method: 'delete',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id
                })
            })
                .then(response => response.json())
                .then(company => {
                    this.props.deleteCompanyFromState(id)
                })
                .catch(err => console.log(err))
        }

    }

    render() {

        const companies = this.props.companies.map(company => {
            return (
                <tr key={company.id}>
                    <th scope="row">{company.id}</th>
                    <td>{company.name}</td>

                    <td>
                        <div style={{width:"110px"}}>
                            <CompanyModalForm buttonLabel="Edit" company={company} updateState={this.props.updateState}/>
                            {' '}
                            <Button color="danger" onClick={() => this.deleteCompany(company.id)}>Del</Button>
                        </div>
                    </td>
                </tr>
            )
        })

        return (
            <Table responsive hover>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>

                </tr>
                </thead>
                <tbody>
                {companies}
                </tbody>
            </Table>
        )
    }
}

export default CompanyDataTable