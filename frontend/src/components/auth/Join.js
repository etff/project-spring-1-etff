import "antd/dist/antd.css";
import {Button, Col, Form, Input, Row, Typography} from "antd";
import {Content} from "antd/lib/layout/layout";
import {useDispatch} from "react-redux";
import {REGISTER_REQUEST} from "../../redux/types";

const {Title} = Typography;

const layout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 16,
  },
};

const Join = () => {
  const dispatch = useDispatch();

  const onFinish = (values) => {
    const {name, email, password} = values;
    const newMember = {name, email, password};
    dispatch({
      type: REGISTER_REQUEST,
      payload: newMember,
    });
  };

  const validateMessages = {
    required: "${label} is required!",
    types: {
      email: "${label} is not a valid email!",
      password: "${label} is not a valid password!",
    },
    password: {
      range: "${label} must be between ${min} and ${max}",
    },
  };

  return (
      <>
        <Content>
          <div style={{padding: 60, minHeight: "80vh"}}>
            <Row>
              <Col span={4} offset={10}>
                <Title>회원가입</Title>
              </Col>
            </Row>
            <Row>
              <Col span={8} push={6}>
                <Form
                    {...layout}
                    name="register"
                    onFinish={onFinish}
                    validateMessages={validateMessages}
                    scrollToFirstError
                >
                  <Form.Item
                      name="email"
                      label="E-mail"
                      rules={[
                        {
                          type: "email",
                          message: "유효한 이메일이 아닙니다!",
                        },
                        {
                          required: true,
                          message: "이메일을 입력해주세요!",
                        },
                      ]}
                  >
                    <Input/>
                  </Form.Item>
                  <Form.Item
                      name="name"
                      label="이름"
                      rules={[
                        {
                          required: true,
                          message: "이름을 입력해주세요!",
                        },
                      ]}
                  >
                    <Input/>
                  </Form.Item>

                  <Form.Item
                      name="password"
                      label="비밀번호"
                      rules={[
                        {
                          required: true,
                          message: "비밀번호를 입력해주세요",
                        },
                        {
                          min: 1,
                          max: 24,
                          message: "비밀번호가 유효하지 않습니다!",
                        },
                      ]}
                      hasFeedback
                  >
                    <Input.Password/>
                  </Form.Item>

                  <Form.Item
                      name="confirm"
                      label="비밀번호 확인"
                      dependencies={["password"]}
                      hasFeedback
                      rules={[
                        {
                          required: true,
                          message: "비밀번호를 확인해주세요!",
                        },
                        ({getFieldValue}) => ({
                          validator(_, value) {
                            if (!value || getFieldValue("password") === value) {
                              return Promise.resolve();
                            }

                            return Promise.reject(
                                new Error("비밀번호가 일치하지 않습니다!")
                            );
                          },
                        }),
                      ]}
                  >
                    <Input.Password/>
                  </Form.Item>

                  <Form.Item wrapperCol={{...layout.wrapperCol, offset: 8}}>
                    <Button
                        type="primary"
                        htmlType="submit"
                        className="login-form-button"
                    >
                      확인
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

export default Join;
