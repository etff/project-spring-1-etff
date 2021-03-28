import {Layout} from "antd";
import Main from "../components/meet/Main";
import Login from "../components/auth/Login";
import Join from "../components/auth/Join";
import NavBar from "../components/NavBar";
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import {Content} from "antd/lib/layout/layout";
import Foot from "../components/Foot";

const MyRouter = () => {
  return (
    <>
      <Router>
        <Layout>
          <NavBar />
          <Layout
            className="site-layout-background"
            style={{ padding: "24px 0" }}
          >
            <Content style={{ padding: "0 50px" }}>
              <Route path="/" exact component={Main} />
              <Route path="/login" exact component={Login} />
              <Route path="/join" exact component={Join} />
              <Redirect from="*" to="/" />
            </Content>
            <Foot />
          </Layout>
        </Layout>
      </Router>
    </>
  );
};

export default MyRouter;
