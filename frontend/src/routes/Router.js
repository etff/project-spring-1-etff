import {Layout} from "antd";
import Main from "../components/meet/Main";
import Login from "../components/auth/Login";
import Join from "../components/auth/Join";
import NavBar from "../components/NavBar";
import {Redirect, Route, Switch,} from "react-router-dom";
import {Content} from "antd/lib/layout/layout";
import Foot from "../components/Foot";

const MyRouter = () => {
  return (
      <>
        <Layout>
          <NavBar/>
          <Layout
              className="site-layout-background"
              style={{padding: "24px 0"}}
          >
            <Content style={{padding: "0 50px"}}>
              <Switch>
                <Route path="/" exact component={Main}/>
                <Route path="/login" exact component={Login}/>
                <Route path="/join" exact component={Join}/>
                <Redirect from="*" to="/"/>
              </Switch>
            </Content>
            <Foot/>
          </Layout>
        </Layout>
      </>
  );
};

export default MyRouter;
