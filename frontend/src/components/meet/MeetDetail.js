import React, {useEffect} from "react";
import {Avatar, Button, Card, Col, Divider, Layout, Row, Space, Typography,} from "antd";
import {UserOutlined} from "@ant-design/icons";
import {useDispatch, useSelector} from "react-redux";
import {Helmet} from "react-helmet";
import "antd/dist/antd.css";
import {MEET_DETAIL_LOADING_REQUEST, MEMBER_LOADING_REQUEST,} from "../../redux/types";
import MeetInfo from "./MeetInfo";
import MeetJoin from "./MeetJoin";
import MeetAttendee from "./MeetAttendee";

const {Content} = Layout;
const {Title} = Typography;
const {Meta} = Card;

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

const MeetDetail = (req) => {
  const dispatch = useDispatch();
  const {memberId, memberName} = useSelector((state) => state.auth);

  const {meetDetail, title, loading} = useSelector((state) => state.meet);

  useEffect(() => {
    dispatch({
      type: MEET_DETAIL_LOADING_REQUEST,
      payload: req.match.params.id,
    });
    dispatch({
      type: MEMBER_LOADING_REQUEST,
      payload: localStorage.getItem("token"),
    });
  }, [dispatch, req.match.params.id]);

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
            {meetDetail && meetDetail.studies ? (
                <>
                  <MeetInfo meetDetail={meetDetail}/>
                  <Divider/>
                  <MeetJoin meetDetail={meetDetail} memberId={memberId}/>
                  <Divider/>
                  <MeetAttendee meetDetail={meetDetail}/>
                </>
            ) : (
                <h1>no data</h1>
            )}
          </div>
        </Content>
      </>
  );
};

export default MeetDetail;
