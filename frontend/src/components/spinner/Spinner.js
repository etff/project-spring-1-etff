import "antd/dist/antd.css";
import {Col, Row, Spin} from "antd";

export const GrowingSpinner = (
    <>
        <Row>
            <Col span={12}>
                <Spin/>
            </Col>
        </Row>
    </>
);
