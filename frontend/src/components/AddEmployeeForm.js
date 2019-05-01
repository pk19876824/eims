import React from 'react';
import 'antd/dist/antd.css';
import {
    Drawer, Form, Button, Col, Row, Input, Select, DatePicker, Icon,
} from 'antd';
import reqwest from 'reqwest';


const {Option} = Select;

class AddEmployeeForm extends React.Component {

    static ADD_EMPLOYEE_URL = "http://localhost:8888/employee";

    constructor(props) {
        super(props);
        this.state = {
            visible: false,
        };
        this.updateTableData = this.updateTableData.bind(this);
    }


    showDrawer = () => {
        this.setState({
            visible: true,
        });
    };

    onClose = () => {
        this.setState({
            visible: false,
        });
    };

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                this.fetch(values);
            }
        });
    };

    fetch = (params = {}) => {
        console.log('params:', params);
        reqwest({
            url: AddEmployeeForm.ADD_EMPLOYEE_URL,
            method: 'post',
            data: {
                ...params,
            },
            type: 'json',
        }).then((data) => {
            if (data.status.code === 0) {
                this.setState({
                    visible: false,
                });
                this.updateTableData();
            } else {
                alert(data.status.msg);
            }
        });
    };

    updateTableData() {
        this.props.updateTableData();
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        const formItemLayout = {
            labelCol: {
                xs: {span: 24},
                sm: {span: 8},
            },
            wrapperCol: {
                xs: {span: 24},
                sm: {span: 16},
            },
        };
        return (
            <div>
                <Button type="primary" onClick={this.showDrawer}>
                    <Icon type="plus"/>添加员工
                </Button>
                <Drawer
                    title="添加员工"
                    width={400}
                    onClose={this.onClose}
                    visible={this.state.visible}
                >
                    <Form {...formItemLayout} onSubmit={this.handleSubmit.bind(this)}>
                        <Form.Item
                            label="姓名"
                        >
                            {getFieldDecorator('name', {
                                rules: [{
                                    required: true, message: '请输入姓名!',
                                }],
                            })(
                                <Input/>
                            )}
                        </Form.Item>
                        <Form.Item label="性别">
                            {getFieldDecorator('gender', {
                                rules: [{
                                    required: true, message: '请选择性别!',
                                }],
                            })(
                                <Select placeholder="请选择性别">
                                    <Option value="0">男</Option>
                                    <Option value="1">女</Option>
                                </Select>
                            )}
                        </Form.Item>
                        <Form.Item label="出生日期">
                            {getFieldDecorator('birthday', {
                                rules: [{
                                    required: true, message: '请选择出生日期!',
                                }],
                            })(
                                <DatePicker placeholder="请选择生日"/>
                            )}
                        </Form.Item>
                        <Form.Item label="部门">
                            {getFieldDecorator('department', {
                                rules: [{
                                    required: true, message: '请选择部门!',
                                }],
                            })(
                                <Select placeholder="请选择部门">
                                    <Option value="1">产品部</Option>
                                    <Option value="2">技术部</Option>
                                    <Option value="3">运营部</Option>
                                    <Option value="4">行政部</Option>
                                    <Option value="5">采购部</Option>
                                </Select>
                            )}
                        </Form.Item>
                        <Form.Item label="职位">
                            {getFieldDecorator('level', {
                                rules: [{
                                    required: true, message: '请选择职位!',
                                }],
                            })(
                                <Select placeholder="请选择职位">
                                    <Option value="1">普通职员</Option>
                                    <Option value="2">组长</Option>
                                    <Option value="3">经理</Option>
                                    <Option value="4">总监</Option>
                                    <Option value="5">董事长</Option>
                                </Select>
                            )}
                        </Form.Item>
                        <div
                            style={{
                                position: 'absolute',
                                left: 0,
                                bottom: 0,
                                width: '100%',
                                borderTop: '1px solid #e9e9e9',
                                padding: '10px 16px',
                                background: '#fff',
                                textAlign: 'right',
                            }}
                        >
                            <Button onClick={this.onClose} style={{marginRight: 8}}>
                                取消
                            </Button>
                            <Button htmlType="submit" type="primary">
                                确定
                            </Button>
                        </div>
                    </Form>
                </Drawer>
            </div>
        );
    }
}

//const WrappedAddEmployeeForm = Form.create({name: 'register'})(AddEmployeeForm);
//export default WrappedAddEmployeeForm;
export default Form.create()(AddEmployeeForm);