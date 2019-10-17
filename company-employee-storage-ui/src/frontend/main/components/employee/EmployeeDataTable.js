import React, {Component} from 'react'
import {Button, Table} from 'reactstrap';
import EmployeeModalForm from './EmployeeModalForm'

class EmployeeDataTable extends Component {

    deleteEmployee = id => {
        let confirmDelete = window.confirm('Delete employee forever?')
        if (confirmDelete) {
            fetch('../api/v1/employee', {
                method: 'delete',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id:id
                })
            })
                .then(response => response.json())
                .then(employee => {
                    this.props.deleteEmployeeFromState(id)
                })
                .catch(err => console.log(err))
        }

    }

    render() {

        const employeeList = this.props.employees &&  this.props.employees.map(employee => {
            return employee && (
                <tr key={employee.id}>
                    <th scope="row">{employee.id}</th>
                    <td>{employee.name}</td>
                    <td>{employee.surname}</td>
                    <td>{employee.email}</td>
                    <td>{employee.address}</td>
                    <td>{employee.salary}</td>
                    <td>
                        <div style={{width: "110px"}}>
                            <EmployeeModalForm buttonLabel="Edit" employee={employee}
                                               updateEmployeeState={this.props.updateEmployeeState}
                                               company={employee.company}/>
                            {' '}
                            <Button color="danger" onClick={() => this.deleteEmployee(employee.id)}>Del</Button>
                        </div>
                    </td>
                </tr>
            )
        })

        return (
            <div className="container-fluid">

            <Table responsive hover>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Salary</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {employeeList}
                </tbody>
            </Table>
            </div>
        )
    }
}

export default EmployeeDataTable