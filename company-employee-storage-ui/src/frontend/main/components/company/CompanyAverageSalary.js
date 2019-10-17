import React, {Component} from 'react'

class CompanyAverageSalary extends Component {
    constructor(props) {
        super(props);
        this.state = {avgSalary: 0, employees:[]};
    }

    componentDidMount() {
        this.getAvgSalary()
    }

    getAvgSalary() {
        const encodedIDValue = encodeURIComponent(this.props.companyID);

        fetch(`../api/v1/company/${encodedIDValue}/salary/avg`, {
            method: 'get',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => response.json())
            .then((responseData) => {
                this.setState({avgSalary: responseData})
                return responseData;
            })
            .catch(err => console.log(err));
    }

    render() {
        return (
            <div>
                <p className="card-text">Avg. Salary:  {this.state.avgSalary.toLocaleString('de-GE', { style: 'currency', currency: 'EUR' }) }</p>
            </div>
        )
    }
}

export default CompanyAverageSalary