import {Avatar, Button, Card, Col, Divider, Image, Layout, Row, Space, Typography,} from "antd";
import {UserOutlined} from "@ant-design/icons";
import {Helmet} from "react-helmet";
import "antd/dist/antd.css";

const {Content} = Layout;
const {Title} = Typography;
const {Meta} = Card;

const MeetingInfo = (
    <>
      <Row>
        <Col span={12}>
          <Image src="https://cdn.pixabay.com/photo/2021/02/06/07/02/laptop-5987093_960_720.jpg"/>
        </Col>
        <Col span={11} push={1}>
          <Title>같이 모각코 하실래요?</Title>
          <h2>2021. 04. 03 14:00 ~ 18:00</h2>
          <h2>리더 : John</h2>
          <h2>
            <a href="#">홍대</a>
          </h2>
          <h2>문의하기</h2>
        </Col>
      </Row>
    </>
);

const MeetingJoin = (
    <>
      <Row>
        <Col span={12}>
          <p>
            주말에 열정적으로 코딩할 사람들 모입시다 ㅎㅎ
            <br/>
            주말에 열정적으로 코딩할 사람들 모입시다 ㅎㅎ
            <br/>
          </p>
        </Col>
        <Col span={11} push={1}>
          <Button type="primary" danger size="large">
            참가하기
          </Button>
        </Col>
      </Row>
    </>
);

const Attendee = (
    <>
      <Row style={{marginBottom: 30}}>
        <Col>참가자 수 : 1명</Col>
      </Row>
      <Row gutter={16}>
        <Space>
          <Card hoverable justify="center" align="middle">
            <Avatar
                size={64}
                icon={<UserOutlined/>}
                style={{marginBottom: 30}}
            />
            <Meta title="Europe Street beat" description="www.instagram.com"/>
          </Card>
          <Card hoverable justify="center" align="middle">
            <Avatar
                size={64}
                icon={<UserOutlined/>}
                style={{marginBottom: 30}}
            />
            <Meta title="Europe Street beat" description="www.instagram.com"/>
          </Card>
        </Space>
      </Row>
    </>
);

const MeetDetail = () => {
  return (
      <>
        <Helmet title="Details"/>

        <Content
            className="site-layout"
            style={{padding: "0 200px", marginTop: 30}}
        >
          <div
              className="site-layout-background"
              style={{padding: 24, minHeight: 380}}
          >
            {MeetingInfo}
            <Divider/>
            {MeetingJoin}
            <Divider/>
            {Attendee}
          </div>
        </Content>
      </>
  );
};

export default MeetDetail;
