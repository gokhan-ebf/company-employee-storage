import React, {Component} from 'react'
import {Button} from 'reactstrap';
import CompanyModalForm from './CompanyModalForm'
import CompanyAverageSalary from './CompanyAverageSalary'

class CompanyDataTable extends Component {

    deleteCompany = id => {
        let confirmDelete = window.confirm('Delete company forever? (Employee records that belong to company will be deleted too!)')
        if (confirmDelete) {
            fetch('../api/v1/company', {
                method: 'delete',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id:id,
                    name:''
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
                <div className="card" key={company.id}  onClick={() => this.props.employeeChangeState(company.id)}>
                    <div className="card-body">
                        <h5 className="card-title">{company.name}</h5>
                        <CompanyAverageSalary companyID={company.id} calculateAvgSalary={this.props.calculateAvgSalary}/>
                        <div className="row">
                            <div className="col-xs-6">
                                <CompanyModalForm buttonLabel="Edit" company={company}
                                                  updateCompanyState={this.props.updateCompanyState}/></div>
                                {' '}

                            <div className="col-xs-6">
                                <Button color="danger" onClick={() => this.deleteCompany(company.id)}>Del</Button>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })

        return (

            <div>
                {companies}
            </div>

        )
    }
}

export default CompanyDataTable