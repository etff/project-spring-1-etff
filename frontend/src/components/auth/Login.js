import {useEffect, useState} from "react";
import "antd/dist/antd.css";
import {Button, Col, Form, Input, Row, Typography} from "antd";
import {Helmet} from "react-helmet";
import {Content} from "antd/lib/layout/layout";
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import {useDispatch, useSelector} from "react-redux";
import {LOGIN_REQUEST} from "../../redux/types";

const {Title} = Typography;
const Login = () => {
  const [localMsg, setLocalMsg] = useState("");
  const dispatch = useDispatch();
  const {errorMsg} = useSelector((state) => state.auth);
  useEffect(() => {
    try {
      setLocalMsg(errorMsg);
    } catch (e) {
      console.log(e);
    }
  }, [errorMsg]);

  const onFinish = (values) => {
    const {email, password} = values;
    const member = {email, password};

    dispatch({
      type: LOGIN_REQUEST,
      payload: member,
    });
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
                          message: "이메일을 입력해주세요!",
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
                          message: "비밀번호를 입력해주세요!",
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
