const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

// tag::app[]
class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {companies: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '../api/v1/companies'}).done(response => {
            this.setState({companies: response.entity});
        });
    }

    render() {
        return (
            <CompanyList companies={this.state.companies}/>
        )
    }
}
// end::app[]

// tag::company-list[]
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
// end::company-list[]

// tag::company[]
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
// end::company[]

// tag::render[]
ReactDOM.render(
    <App />,
    document.getElementById('react')
)
// end::render[]