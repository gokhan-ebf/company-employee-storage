import React from 'react';
import {Button, Form, FormGroup, Input, Label} from 'reactstrap';

class EmployeeFormAddEdit extends React.Component {
    state = {
        id: 0,
        name: '',
        surname: '',
        email: '',
        address: '',
        salary: '',
        company: []
    }

    onChange = e => {
        this.setState({[e.target.name]: e.target.value})
    }

    submitFormAdd = e => {
        e.preventDefault()
        fetch('../api/v1/employee', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: this.state.name,
                surname: this.state.surname,
                email: this.state.email,
                address: this.state.address,
                salary: this.state.salary,
                company: this.props.company
            })
        })
            .then(response => response.json())
            .then(employee => {
                if (employee !==null && employee.name.length > 0) {
                    this.props.addEmployeeToState(employee)
                    this.props.toggle()
                } else {
                    console.log('failure')
                }
            })
            .catch(err => console.log(err))
    }

    submitFormEdit = e => {
        e.preventDefault()
        fetch('../api/v1/employee', {
            method: 'put',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: this.state.id,
                name: this.state.name,
                surname: this.state.surname,
                email: this.state.email,
                address: this.state.address,
                salary: this.state.salary,
                company: this.props.company
            })
        })
            .then(response => response.json())
            .then(employee => {
                if (employee.name.length > 0) {
                    this.props.updateEmployeeState(employee)
                    this.props.toggle()
                }else {
                    console.log('failure')
                }
            })
            .catch(err => console.log(err))
    }

    componentDidMount(){
        // if employee exists, populate the state with proper data
        if(this.props.employee){
            const { id, name, surname, email, address, salary, company } = this.props.employee
            this.setState({ id, name, surname, email, address, salary, company })
        }
    }

    render() {
        return (
            <Form onSubmit={this.props.employee ? this.submitFormEdit : this.submitFormAdd}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" id="name" onChange={this.onChange} value={this.state.name === null ? '' : this.state.name} />
                </FormGroup>
                <FormGroup>
                    <Label for="surname">Surname</Label>
                    <Input type="text" name="surname" id="surname" onChange={this.onChange} value={this.state.surname === null ? '' : this.state.surname}  />
                </FormGroup>
                <FormGroup>
                    <Label for="email">Email</Label>
                    <Input type="email" name="email" id="email" onChange={this.onChange} value={this.state.email === null ? '' : this.state.email}  />
                </FormGroup>
                <FormGroup>
                    <Label for="address">Address</Label>
                    <Input type="text" name="address" id="address" onChange={this.onChange} value={this.state.address === null ? '' : this.state.address}  placeholder="City, State" />
                </FormGroup>
                <FormGroup>
                    <Label for="salary">Salary</Label>
                    <Input type="text" name="salary" id="salary" onChange={this.onChange} value={this.state.salary === null ? '' : this.state.salary}  />
                </FormGroup>

                <Button>Submit</Button>
            </Form>
        );
    }
}


export default EmployeeFormAddEdit