import React from "react";
import {Grid, Menu} from "antd";
import {Link} from "react-router-dom";

const {useBreakpoint} = Grid;

const LeftMenu = () => {
    const {md} = useBreakpoint();
    return (
        <Menu mode={md ? "horizontal" : "inline"}>
            <Menu.Item key="home">
                <Link to="/">Home</Link>
            </Menu.Item>
        </Menu>
    );
};

export default LeftMenu;
