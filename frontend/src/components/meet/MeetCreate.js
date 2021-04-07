import "antd/dist/antd.css";
import {Button, Carousel, Col, DatePicker, Form, Input, InputNumber, Row, Typography,} from "antd";
import {Content} from "antd/lib/layout/layout";
import {useDispatch} from "react-redux";
import {SearchOutlined} from "@ant-design/icons";
import {Helmet} from "react-helmet";
import {MEET_CREATE_REQUEST} from "../../redux/types";

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

  const onFinish = (values) => {
    const {
      startedAt,
      time,
      location,
      count,
      title,
      message,
      subject,
    } = values;
    const token = localStorage.getItem("token");

    const createMeeting = {
      startedAt,
      time,
      location,
      count,
      title,
      message,
      subject,
      token,
    };

    dispatch({
      type: MEET_CREATE_REQUEST,
      payload: createMeeting,
    });
  };

  const validateMessages = {
    required: "${label} is required!",
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
                      <Input placeholder="ex) 00:00 ~ 14:00"/>
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
                      <Input
                          prefix={<SearchOutlined/>}
                          placeholder="위치 / 상세주소로 입력하세요 예) 홍대 / 스타벅스"
                      />
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
                    <Form.Item
                        name="message"
                        label="메시지"
                        rules={[
                          {
                            required: true,
                            message: "본문을 입력해주세요!",
                          },
                        ]}
                    >
                      <TextArea/>
                    </Form.Item>

                    <Form.Item name="subject" label="나의 주제">
                      <Input placeholder="모각코에서 공부할 주제를 입력하세요"/>
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
