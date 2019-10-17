import React from 'react';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';

class CompanyFormAddEdit extends React.Component {
  state = {
    id: 0,
    name: ''
  }

  onChange = e => {
    this.setState({[e.target.name]: e.target.value})
  }

  submitFormAdd = e => {
    e.preventDefault()
    fetch('../api/v1/company', {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        name: this.state.name
      })
    })
      .then(response => response.json())
      .then(company => {
        if(Array.isArray(company)) {
          this.props.addCompanyToState(company[0])
          this.props.toggle()
        } else {
          console.log('failure')
        }
      })
      .catch(err => console.log(err))
  }

  submitFormEdit = e => {
    e.preventDefault()
    fetch('api/v1/company', {
      method: 'put',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        id: this.state.id,
        name: this.state.name
      })
    })
      .then(response => response.json())
      .then(company => {
        if(Array.isArray(company)) {
          // console.log(company[0])
          this.props.updateState(company[0])
          this.props.toggle()
        } else {
          console.log('failure')
        }
      })
      .catch(err => console.log(err))
  }

  componentDidMount(){
    // if company exists, populate the state with proper data
    if(this.props.company){
      const { id, name} = this.props.company
      this.setState({ id, name})
    }
  }

  render() {
    return (
      <Form onSubmit={this.props.company ? this.submitFormEdit : this.submitFormAdd}>
        <FormGroup>
          <Label for="first">Name</Label>
          <Input type="text" name="name" id="name" onChange={this.onChange} value={this.state.name === null ? '' : this.state.name} />
        </FormGroup>
        <Button>Submit</Button>
      </Form>
    );
  }
}

export default CompanyFormAddEdit