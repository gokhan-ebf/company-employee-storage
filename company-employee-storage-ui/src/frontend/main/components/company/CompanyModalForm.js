import React, { Component } from 'react'
import { Button, Modal, ModalHeader, ModalBody } from 'reactstrap'
import AddEditForm from './CompanyFormAddEdit'

class CompanyModalForm extends Component {
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

        if(label === 'Edit'){
            button = <Button
                color="warning"
                onClick={this.toggle}
                style={{float: "left", marginRight:"10px"}}>{label}
            </Button>
            title = 'Edit Company'
        } else {
            button = <Button
                color="success"
                onClick={this.toggle}
                style={{float: "left", marginRight:"10px"}}>{label}
            </Button>
            title = 'Add New Company'
        }


        return (
            <div>
                {button}
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle} close={closeBtn}>{title}</ModalHeader>
                    <ModalBody>
                        <AddEditForm
                            addCompanyToState={this.props.addCompanyToState}
                            updateState={this.props.updateState}
                            toggle={this.toggle}
                            company={this.props.company} />
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}

export default CompanyModalForm