import {Avatar, Card, Checkbox, Col, Row, Space} from "antd";
import {UserOutlined} from "@ant-design/icons";

const {Meta} = Card;

const MeetAttendee = ({meetDetail, memberId}) => {
    return (
        <>
            <Row style={{marginBottom: 30}}>
                <Col>참가자 수 : {meetDetail.studies.length}명</Col>
            </Row>
            <Row gutter={16}>
                <Col span>
                    <Space>
                        {Array.isArray(meetDetail.studies)
                            ? meetDetail.studies.map(
                                ({id, subject, member, position, status}) => {
                                    return (
                                        <Card hoverable justify="center" align="middle" key={id}>
                                            <Avatar
                                                size={64}
                                                icon={<UserOutlined/>}
                                                style={{marginBottom: 30}}
                                            />

                                            <Meta title={member.name} description={subject}/>
                                            <Row>
                                                <Col span={20}>
                                                    {position === "LEADER" ? "리더" : "리더승인"}
                                                </Col>
                                                <Col span={4}>
                                                    {status === "APPROVED" ? (
                                                        <Checkbox defaultChecked disabled/>
                                                    ) : (
                                                        <Checkbox defaultChecked={false} disabled/>
                                                    )}
                                                </Col>
                                            </Row>
                                        </Card>
                                    );
                                }
                            )
                            : ""}
                    </Space>
                </Col>
            </Row>
        </>
  );
};

export default MeetAttendee;
