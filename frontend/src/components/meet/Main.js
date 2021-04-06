import {Breadcrumb, Button, Card, Layout, PageHeader, Row} from "antd";
import {useEffect} from "react";
import {Helmet} from "react-helmet";
import "antd/dist/antd.css";
import {useDispatch, useSelector} from "react-redux";
import {MEET_LOADING_REQUEST} from "../../redux/types";
import {GrowingSpinner} from "../spinner/Spinner";
import MeetCardOne from "../meet/MeetCardOne";

const {Content} = Layout;
const {Meta} = Card;

const Main = () => {
    const {meets} = useSelector((state) => state.meet);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch({type: MEET_LOADING_REQUEST});
  }, [dispatch]);

  return (
    <>
        <Helmet title="Home"/>
        <PageHeader
            title="이번주 모각코"
            className="site-page-header"
            style={{margin: 50}}
            extra={[
                <Button key="1" type="primary">
                    모임 만들기
                </Button>,
            ]}
        ></PageHeader>
        <Content
            className="site-layout"
            style={{padding: "0 50px", marginTop: 30}}
        >
            <Breadcrumb style={{margin: "16px 0"}}>
                <Breadcrumb.Item>
                    <h2>이번주 모각코</h2>
                </Breadcrumb.Item>
            </Breadcrumb>
        <div
          className="site-layout-background"
          style={{ padding: 24, minHeight: 380 }}
        >
          <div className="site-card-wrapper">
            <Row gutter={16}>
                {meets ? <MeetCardOne meets={meets}/> : GrowingSpinner}
            </Row>
          </div>
        </div>
      </Content>
    </>
  );
};

export default Main;
