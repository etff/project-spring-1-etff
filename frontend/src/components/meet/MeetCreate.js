import "antd/dist/antd.css";
import {Button, Carousel, Col, DatePicker, Form, Input, InputNumber, Row, TimePicker, Typography,} from "antd";
import {Content} from "antd/lib/layout/layout";
import {useDispatch} from "react-redux";
import {Helmet} from "react-helmet";
import moment from "moment";

const {Title} = Typography;
const {TextArea} = Input;

const layout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 16,
  },
};

const contentStyle = {
  height: "160px",
  color: "#fff",
  lineHeight: "160px",
  textAlign: "center",
  background: "#364d79",
};

const MeetCreate = () => {
  const dispatch = useDispatch();

  function onChange(a, b, c) {
    console.log(a, b, c);
  }

  const onFinish = (values) => {
    const {name, email, password} = values;
    const newMember = {name, email, password};
  };

  const format = "HH:mm";

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
        <Helmet title="Create Meeting"/>
        <Content
            className="site-layout"
            style={{padding: "0 50px", marginTop: 30}}
        >
          <Carousel autoplay>
            <div>
              <h3 style={contentStyle}>모여서 각자 코딩해요</h3>
            </div>
            <div>
              <h3 style={contentStyle}>친구들을 모아요</h3>
            </div>
            <div>
              <h3 style={contentStyle}>모각코!</h3>
            </div>
            <div>
              <h3 style={contentStyle}>모각코!</h3>
            </div>
          </Carousel>

          <div
              className="site-layout-background"
              style={{padding: 24, minHeight: 380}}
          >
            <div style={{padding: 60, minHeight: "80vh"}}>
              <Row>
                <Col span={5} offset={10}>
                  <Title>모각코 모집하기</Title>
                </Col>
              </Row>
              <Row>
                <Col span={8} push={7}>
                  <Form
                      {...layout}
                      name="create"
                      onFinish={onFinish}
                      validateMessages={validateMessages}
                      scrollToFirstError
                  >
                    <Form.Item
                        name="startedAt"
                        label="날짜"
                        rules={[
                          {
                            required: true,
                            message: "날짜를 입력해주세요!",
                          },
                        ]}
                    >
                      <DatePicker className="login-form-button"/>
                    </Form.Item>
                    <Form.Item
                        name="time"
                        label="시간"
                        rules={[
                          {
                            required: true,
                            message: "시간을 입력해주세요!",
                          },
                        ]}
                    >
                      <TimePicker
                          defaultValue={moment("00:00", format)}
                          format={format}
                          className="login-form-button"
                      />
                    </Form.Item>
                    <Form.Item
                        name="location"
                        label="장소"
                        rules={[
                          {
                            required: true,
                            message: "장소를 입력해주세요!",
                          },
                        ]}
                    >
                      <Input/>
                    </Form.Item>
                    <Form.Item
                        name="count"
                        label="인원"
                        rules={[
                          {
                            required: true,
                            message: "최소인원을 입력해주세요",
                          },
                        ]}
                    >
                      <InputNumber min={1} className="login-form-button"/>
                    </Form.Item>

                    <Form.Item
                        name="title"
                        label="제목"
                        rules={[
                          {
                            required: true,
                            message: "제목을 입력해주세요!",
                          },
                        ]}
                    >
                      <Input/>
                    </Form.Item>
                    <Form.Item name="message" label="메시지">
                      <TextArea/>
                    </Form.Item>

                    <Form.Item name="subject" label="나의 주제">
                      <Input/>
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
          </div>
        </Content>
      </>
  );
};

export default MeetCreate;
