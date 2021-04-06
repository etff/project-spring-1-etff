import {useCallback} from "react";
import {Header} from "antd/lib/layout/layout";
import {HomeTwoTone} from "@ant-design/icons";
import {useDispatch, useSelector} from "react-redux";
import {Col, Menu, Row} from "antd";
import {Link} from "react-router-dom";
import {LOGOUT_REQUEST} from "../redux/types";

const NavBar = () => {
  const dispatch = useDispatch();

  const {isAuthenticated} = useSelector((state) => state.auth);

  const onLogout = useCallback(() => {
    dispatch({
      type: LOGOUT_REQUEST,
    });
  }, [dispatch]);

  return (
      <Header>
        <Row>
          <Col span={1}/>
          <Col span={19}>
            <div className="icons-list">
              <Link to="/">
                <HomeTwoTone style={{fontSize: "20px"}}/>
              </Link>
            </div>
          </Col>
          <Col span={4}>
            <Menu theme="dark" mode="horizontal">
              {!isAuthenticated ? (
                  <Menu.Item key="1">
                    <Link to="/login">LOGIN</Link>
                  </Menu.Item>
              ) : (
                  <Menu.Item key="1">
                    <Link onClick={onLogout} to="#">
                      Logout
                    </Link>
                  </Menu.Item>
              )}

              <Menu.Item key="2">
                <Link to="/join">Join</Link>
              </Menu.Item>
            </Menu>
          </Col>
        </Row>
      </Header>
  );
};

export default NavBar;
