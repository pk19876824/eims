import React from 'react';
import {Layout} from 'antd';
import "antd/dist/antd.css";
import MyTable from './components/MyTable'
import AddEmployeeForm from './components/AddEmployeeForm'

const {Header, Content, Footer} = Layout;

function App() {
    return (
        <Layout className="layout">
            <Header>
                <h1 style={{color: "white", textAlign: "center"}}>员工信息管理系统</h1>
            </Header>
            <Content style={{padding: '0 50px'}}>
                <div style={{background: '#fff', padding: 24, minHeight: 800}}>
                    <MyTable/>
                </div>
            </Content>
            <Footer style={{textAlign: 'center'}}>
                Employee Information Management System ©2019 Created by React
            </Footer>
        </Layout>
    );
}

export default App;
