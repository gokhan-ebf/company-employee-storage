import EmployeeModalForm from "./components/employee/EmployeeModalForm";
import React from 'react'
import {Container} from 'reactstrap'
import CompanyModalForm from './components/company/CompanyModalForm'
import CompanyDataTable from './components/company/CompanyDataTable'
import EmployeeDataTable from "./components/employee/EmployeeDataTable";
import {CSVLink} from "react-csv"
import 'bootstrap/dist/css/bootstrap.min.css';

const ReactDOM = require('react-dom');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {companies: [], employees: [], currentCompanyID: 0}
    }

    componentDidMount() {
        this.getCompanies()
    }

    getCompanies() {
        fetch('../api/v1/company')
            .then(response => response.json())
            .then(companies => this.setState({companies: companies}))
            .catch(err => console.log(err))
    }

    getEmployees = (currentCompanyID) => {
        const encodedIDValue = encodeURIComponent(currentCompanyID);
        fetch(`../api/v1/company/${encodedIDValue}/employees`, {
            method: 'get',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then((responseData) => {
                this.setState({employees: responseData})
                this.setState({currentCompanyID: currentCompanyID})
                return responseData;
            })
            .catch(err => console.log(err))
    }


    addCompanyToState = (company) => {
        this.setState(prevState => ({
            companies: [...prevState.companies, company]
        }))
    }

    addEmployeeToState = (employee) => {
        this.setState(prevState => ({
            employees: [...prevState.employees, employee]
        }))
    }

    updateCompanyState = (company) => {
        const companyIndex = this.state.companies.findIndex(data => data.id === company.id)
        const newArray = [
            // destructure all companies from beginning to the indexed company
            ...this.state.companies.slice(0, companyIndex),
            // add the updated company to the array
            company,
            // add the rest of the companies to the array from the index after the replaced company
            ...this.state.companies.slice(companyIndex + 1)
        ]
        this.setState({companies: newArray})

    }

    deleteCompanyFromState = (id) => {
        const updatedCompanies = this.state.companies.filter(company => company.id !== id)
        this.setState({companies: updatedCompanies})
        this.setState({employees: []})
        this.setState({currentCompanyID: 0})
    }

    deleteEmployeeFromState = (id) => {
        const updatedEmployees = this.state.employees.filter(company => company.id !== id)
        this.setState({employees: updatedEmployees})
    }

    updateEmployeeState = (employee) => {
        const employeeIndex = this.state.employees.findIndex(data => data.id === employee.id)
        const newArray = [
            // destructure all employees from beginning to the indexed company
            ...this.state.employees.slice(0, employeeIndex),
            // add the updated company to the array
            employee,
            // add the rest of the employees to the array from the index after the replaced company
            ...this.state.employees.slice(employeeIndex + 1)
        ]
        this.setState({employees: newArray})
    }

    employeeChangeState = (id) => {
        this.setState({employees: []})
        this.getEmployees(id)

    }

    render() {
        return (
            <Container fluid={true}>
                <div className="row">
                    <div className="col-2">
                        <nav className="navbar navbar-light navbar-expand-sm px-0 flex-row flex-nowrap w-100">
                            <div className="nav flex-sm-column flex-row w-100">
                                <div className="menu-item">
                                    <CSVLink
                                        filename={"company.csv"}
                                        color="primary"
                                        style={{float: "left", marginRight: "10px"}}
                                        className="btn btn-primary"
                                        data={this.state.companies}>
                                        Download Company Data
                                    </CSVLink>
                                </div>
                                <div className="menu-item">
                                    <CompanyModalForm buttonLabel="Add Company"
                                                      addCompanyToState={this.addCompanyToState}/>
                                </div>
                                <CompanyDataTable companies={this.state.companies}
                                                  updateCompanyState={this.updateCompanyState}
                                                  calculateAvgSalary={this.calculateAvgSalary}
                                                  deleteCompanyFromState={this.deleteCompanyFromState}
                                                  employeeChangeState={this.employeeChangeState}
                                                  employees={this.state.employees}/>
                            </div>

                        </nav>
                    </div>
                    <div className="col py-2">
                        {this.state.currentCompanyID > 0 &&
                        <div>
                            <h2>Employees</h2>
                            <div className="float-right">
                                <div className="row">
                                    <EmployeeModalForm buttonLabel="Add Employee"
                                                       addEmployeeToState={this.addEmployeeToState}
                                                       company={{id: this.state.currentCompanyID, name: ''}}/>
                                    <CSVLink
                                        filename={"employee.csv"}
                                        color="primary"
                                        style={{float: "right", marginRight: "10px"}}
                                        className="btn btn-primary"
                                        data={this.state.employees}>
                                        Download Employee Data
                                    </CSVLink>
                                </div>
                            </div>
                            <EmployeeDataTable employees={this.state.employees}
                                               updateEmployeeState={this.updateEmployeeState}
                                               deleteEmployeeFromState={this.deleteEmployeeFromState}
                                               currentCompanyID={this.state.currentCompanyID}
                                               addEmployeeToState={this.addEmployeeToState}/>
                        </div>
                        }
                    </div>
                </div>


            </Container>
        )
    }
}


ReactDOM.render(
    <App/>,
    document.getElementById('react')
)