import {Layout} from "antd";
import Main from "../components/meet/Main";
import Login from "../components/Login";
import Join from "../components/Join";
import NavBar from "../components/NavBar";
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import {Content} from "antd/lib/layout/layout";

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
          </Layout>
        </Layout>
      </Router>
    </>
  );
};

export default MyRouter;
