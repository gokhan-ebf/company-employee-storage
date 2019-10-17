import React, {Component} from 'react'
import {Button, Modal, ModalBody, ModalHeader} from 'reactstrap'
import EmployeeFormAddEdit from './EmployeeFormAddEdit'

class EmployeeModalForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            modal: false
        }
    }

    toggle = () => {
        this.setState(prevState => ({
            modal: !prevState.modal
        }))
    }

    render() {
        const closeBtn = <button className="close" onClick={this.toggle}>&times;</button>

        const label = this.props.buttonLabel

        let button = ''
        let title = ''

        if (label === 'Edit') {
            button = <Button
                color="warning"
                onClick={this.toggle}
                style={{float: "left", marginRight: "10px"}}>{label}
            </Button>
            title = 'Edit Employee'
        } else {
            button = <Button
                color="success"
                onClick={this.toggle}
                style={{float: "left", marginRight: "10px"}}>{label}
            </Button>
            title = 'Add New Employee'
        }


        return (
            <div>
                {button}
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle} close={closeBtn}>{title}</ModalHeader>
                    <ModalBody>
                        <EmployeeFormAddEdit
                            addEmployeeToState={this.props.addEmployeeToState}
                            updateEmployeeState={this.props.updateEmployeeState}
                            company={this.props.company}
                            toggle={this.toggle}
                            employee={this.props.employee}/>
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}

export default EmployeeModalForm