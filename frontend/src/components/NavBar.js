import React from "react";
import { Header } from "antd/lib/layout/layout";
import { HomeOutlined, HomeTwoTone } from "@ant-design/icons";
import { Col, Menu, Row } from "antd";
import { Link } from "react-router-dom";

const NavBar = () => {
  return (
    <Header>
      <div className="logo" />
      <Row>
        <Col span={20}>
          <div className="icons-list">
            <Link to="/">
              <HomeTwoTone />
            </Link>
          </div>
        </Col>
        <Col span={4}>
          <Menu theme="dark" mode="horizontal">
            <Menu.Item key="1">
              <Link to="/login">LOGIN</Link>
            </Menu.Item>
            <Menu.Item key="2">
              <Link to="/join">Join </Link>
            </Menu.Item>
          </Menu>
        </Col>
      </Row>
    </Header>
  );
};

export default NavBar;
