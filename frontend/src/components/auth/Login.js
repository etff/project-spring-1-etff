import "antd/dist/antd.css";
import {Button, Col, Form, Input, Row, Typography} from "antd";
import {Helmet} from "react-helmet";
import {Content} from "antd/lib/layout/layout";
import {LockOutlined, UserOutlined} from "@ant-design/icons";

const {Title} = Typography;
const Login = () => {
  const onFinish = (values) => {
    console.log("Received values of form: ", values);
  };

  return (
      <>
        <Helmet title="Login"/>
        <Content>
          <div style={{padding: 100, minHeight: "80vh"}}>
            <Row>
              <Col span={8} offset={8}>
                <Title>로그인</Title>
              </Col>
            </Row>
            <Row>
              <Col span={8} offset={8}>
                <Form
                    name="normal_login"
                    className="login-form"
                    initialValues={{
                      remember: true,
                    }}
                    onFinish={onFinish}
                >
                  <Form.Item
                      name="email"
                      rules={[
                        {
                          required: true,
                          type: "email",
                          message: "Please input your Email!",
                        },
                      ]}
                  >
                    <Input
                        prefix={<UserOutlined className="site-form-item-icon"/>}
                        placeholder="Email"
                    />
                  </Form.Item>
                  <Form.Item
                      name="password"
                      rules={[
                        {
                          required: true,
                          message: "Please input your Password!",
                        },
                      ]}
                  >
                    <Input
                        prefix={<LockOutlined className="site-form-item-icon"/>}
                        type="password"
                        placeholder="Password"
                    />
                  </Form.Item>
                  <Form.Item>
                    <a className="login-form-forgot" href="">
                      Forgot password
                    </a>
                  </Form.Item>

                  <Form.Item>
                    <Button
                        type="primary"
                        htmlType="submit"
                        className="login-form-button"
                    >
                      Log in
                    </Button>
                  </Form.Item>
                </Form>
              </Col>
            </Row>
          </div>
        </Content>
      </>
  );
};

export default Login;
