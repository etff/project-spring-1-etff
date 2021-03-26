import {Breadcrumb, Card, Col, Layout, PageHeader, Row} from "antd";
import {useEffect} from "react";
import {Helmet} from "react-helmet";
import "antd/dist/antd.css";
import {useDispatch, useSelector} from "react-redux";
import {MEET_LOADING_REQUEST} from "../../redux/types";

const { Content } = Layout;
const { Meta } = Card;

const Main = () => {
  const { meets } = useSelector((state) => state.meet);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch({ type: MEET_LOADING_REQUEST });
  }, [dispatch]);

  return (
    <>
      <Helmet title="Home" />
      <PageHeader
        title="Title"
        className="site-page-header"
        subTitle="This is a subtitle"
        avatar={{
          src: "https://avatars1.githubusercontent.com/u/8186664?s=460&v=4",
        }}
      ></PageHeader>
      <Content
        className="site-layout"
        style={{ padding: "0 50px", marginTop: 30 }}
      >
        <Breadcrumb style={{ margin: "16px 0" }}>
          <Breadcrumb.Item>
            <h1>이번주 모각코</h1>
          </Breadcrumb.Item>
        </Breadcrumb>
        <div
          className="site-layout-background"
          style={{ padding: 24, minHeight: 380 }}
        >
          <Row>{meets ? <h1>Main</h1> : <h1>Spinner</h1>} </Row>
          <div className="site-card-wrapper">
            <Row gutter={16}>
              <Col span={6}>
                <Card
                  hoverable
                  style={{ width: 300 }}
                  cover={
                    <img
                      alt="example"
                      src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"
                    />
                  }
                >
                  <Meta
                    title="Europe Street beat"
                    description="www.instagram.com"
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card
                  hoverable
                  style={{ width: 300 }}
                  cover={
                    <img
                      alt="example"
                      src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"
                    />
                  }
                >
                  <Meta
                    title="Europe Street beat"
                    description="www.instagram.com"
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card
                  hoverable
                  style={{ width: 300 }}
                  cover={
                    <img
                      alt="example"
                      src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"
                    />
                  }
                >
                  <Meta
                    title="Europe Street beat"
                    description="www.instagram.com"
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card
                  hoverable
                  style={{ width: 300 }}
                  cover={
                    <img
                      alt="example"
                      src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"
                    />
                  }
                >
                  <Meta
                    title="Europe Street beat"
                    description="www.instagram.com"
                  />
                </Card>
              </Col>
            </Row>
          </div>
        </div>
      </Content>
    </>
  );
};

export default Main;
