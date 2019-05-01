import React, {Component} from 'react';
import {Button, Modal, Table} from 'antd';
import reqwest from 'reqwest';
import AddEmployeeForm from './AddEmployeeForm'

class MyTable extends Component {

    static url = 'http://localhost:8888/employee';

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            pagination: {},
            loading: false,
            selectedRowKeys: [],
            confirmVisible: false,
            confirmLoading: false,
        };
        //this.handleDelete = this.handleDelete.bind(this);
        //this.showDeleteConfirm = this.showDeleteConfirm.bind(this);
        this.showDeleteConfirmModal = this.showDeleteConfirmModal.bind(this);
        this.handleDeleteCancel = this.handleDeleteCancel.bind(this);
        this.handleDeleteOk = this.handleDeleteOk.bind(this);
    };

    componentDidMount() {
        this.getTableData();
    }

    getTableData = (params = {}) => {
        console.log('params:', params);
        this.setState({loading: true});
        reqwest({
            url: MyTable.url,
            method: 'get',
            data: {
                results: 10,
                ...params,
            },
            type: 'json',
            success: (data) => {
                const pagination = {...this.state.pagination};
                if (data.status.code === 0) {
                    // Read total count from server
                    // pagination.total = data.totalCount;
                    pagination.total = data.data.count;
                    this.setState({
                        data: data.data.results,
                        pagination,
                        loading: false
                    });
                } else {
                    alert(data.status.msg);
                    this.setState({
                        loading: false
                    });
                }
            },
            error: (e) => {
                console.log(e);
                this.setState({
                    loading: false
                })
            }
        });
    };

    /*handleDelete() {
        this.setState({
            deleteLoading: true
        });
        const {selectedRowKeys} = this.state;
        const selectedCount = selectedRowKeys.length;
        confirm({
            title: '确定删除这' + selectedCount + '个员工?',
            //content: selectedRowKeys,
            okText: '确定',
            okType: 'danger',
            cancelText: '取消',
            onOk() {
                console.log("selectedRowKeys:" + Array.from(selectedRowKeys));
                reqwest({
                    url: MyTable.url,
                    method: 'delete',
                    data: {
                        ids: Array.from(selectedRowKeys)
                    },
                    type: 'json',
                    success: (data) => {
                        console.log(data);
                        this.setState({
                            deleteLoading: false,
                            data: []
                        })
                    },
                    error: (e) => {
                        console.log(e);
                    }
                });
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    }*/

    showDeleteConfirmModal() {
        this.setState({
            confirmVisible: true,
        })
    }

    handleDeleteOk() {
        const {selectedRowKeys} = this.state;
        this.setState({
            confirmLoading: true,
        });
        reqwest({
            url: MyTable.url,
            method: 'delete',
            data: {
                ids: Array.from(selectedRowKeys)
            },
            type: 'json',
            success: (data) => {
                console.log(data);
                this.setState({
                    confirmVisible: false,
                    confirmLoading: false,
                    selectedRowKeys: [],
                    data: []
                })
            },
            error: (e) => {
                console.log(e);
            }
        }).then(() => {
            this.getTableData();
        });
    };

    handleDeleteCancel() {
        this.setState({
            confirmVisible: false,
        })
    }

    handleTableChange = (pagination, filters, sorter) => {
        console.log("filters:" + filters);
        console.log("sorter:" + sorter);

        const pager = {...this.state.pagination};
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
        this.getTableData({
            results: pagination.pageSize,
            page: pagination.current,
            sortField: sorter.field,
            sortOrder: sorter.order,
            ...filters,
        });
    };

    onSelectChange = (selectedRowKeys) => {
        console.log('selectedRowKeys changed: ', selectedRowKeys);
        this.setState({selectedRowKeys});
    };

    render() {
        const columns = [{
            title: '姓名',
            dataIndex: 'name',
            render: name => `${name}`,
            width: '20%',
        }, {
            title: '年龄',
            dataIndex: 'age',
            sorter: true,
            width: '20%'
        }, {
            title: '性别',
            dataIndex: 'gender',
            filters: [
                {text: 'Male', value: '0'},
                {text: 'Female', value: '1'},
            ],
            width: '20%',
        }, {
            title: '部门',
            dataIndex: 'department',
            filters: [
                {text: '产品部', value: '1'},
                {text: '技术部', value: '2'},
                {text: '运营部', value: '3'},
                {text: '行政部', value: '4'},
                {text: '采购部', value: '5'},
            ],
            width: '20%',
        }, {
            title: '职位',
            dataIndex: 'level',
            filters: [
                {text: '普通职员', value: '1'},
                {text: '组长', value: '2'},
                {text: '经理', value: '3'},
                {text: '总监', value: '4'},
                {text: '董事长', value: '5'},
            ],
            width: '20%',
        },];

        const {
            deleteLoading,
            selectedRowKeys,
            confirmVisible,
            confirmLoading,
            data,
            pagination,
            loading
        } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;


        return (
            <div>
                <div>
                    <Button
                        type="primary"
                        onClick={this.showDeleteConfirmModal}
                        disabled={!hasSelected}
                        loading={deleteLoading}
                        style={{margin: '10px 10px 10px 10px'}}
                    >
                        Delete
                    </Button>
                    <span style={{marginLeft: 8}}>
                        {hasSelected ? `选择 ${selectedRowKeys.length} 项` : ''}
                    </span>
                    <Modal
                        title="确认删除？"
                        visible={confirmVisible}
                        confirmLoading={confirmLoading}
                        onOk={this.handleDeleteOk}
                        onCancel={this.handleDeleteCancel}
                        okType="danger"
                    >
                        <p>删除后将无法恢复</p>
                    </Modal>
                    <div style={{padding: '10px 10px', float: "right"}}>
                        <AddEmployeeForm updateTableData={this.getTableData.bind(this)}/>
                    </div>
                </div>
                <Table
                    rowSelection={rowSelection}
                    bordered={true}
                    columns={columns}
                    rowKey={record => record.id}
                    dataSource={data}
                    pagination={pagination}
                    loading={loading}
                    onChange={this.handleTableChange}
                />
            </div>

        )
    };
}

export default MyTable;