import React, {useState} from "react";
import {Button, Col, Form, Input, Modal, Row} from "antd";
import {useDispatch} from "react-redux";
import "antd/dist/antd.css";
import {MEET_JOIN_REQUEST} from "../../redux/types";

const CollectionCreateForm = ({visible, onCreate, onCancel}) => {
  const [form] = Form.useForm();
  const [buttonDisabled, setButtonDisabled] = useState(true);
  return (
      <Modal
          visible={visible}
          title="참가하기"
          okText="확인"
          cancelText="취소"
          onCancel={onCancel}
          onOk={() => {
            form
                .validateFields()
                .then((values) => {
                  form.resetFields();
                  onCreate(values);
                })
                .catch((info) => {
                  console.log("Validate Failed:", info);
                });
          }}
          okButtonProps={{disabled: buttonDisabled}}
      >
        <Form
            form={form}
            layout="vertical"
            name="apply"
            initialValues={{
              modifier: "public",
            }}
            onFieldsChange={() =>
                setButtonDisabled(
                    form.getFieldsError().some((field) => field.errors.length > 0)
                )
            }
        >
          <Form.Item
              name="time"
              label="참가시간"
              rules={[
                {
                  required: true,
                  message: "참가할 시간을 입력해주세요!",
                },
              ]}
          >
            <Input placeholder="ex) 00:00 ~ 14:00"/>
          </Form.Item>
          <Form.Item name="subject" label="주제">
            <Input/>
          </Form.Item>
        </Form>
      </Modal>
  );
};

const MeetJoin = ({meetDetail, memberId, isAuthenticated}) => {
  const dispatch = useDispatch();
  const leaderId = meetDetail.studies
      .filter((v) => v.position === "LEADER")
      .map((v) => v.member.id)[0];

  const [visible, setVisible] = useState(false);

  const onCreate = (values) => {
    const {time, subject} = values;
    const token = localStorage.getItem("token");
    const id = meetDetail.meetId;
    const body = {time, subject, token, id};
    dispatch({
      type: MEET_JOIN_REQUEST,
      payload: body,
    });

    setVisible(false);
  };

  return (
      <>
        <Row>
          <Col span={12}>
            <p>{meetDetail.message}</p>
          </Col>

          <Col span={11} push={1}>
            {leaderId !== memberId && isAuthenticated ? (
                <>
                  <Button
                      type="primary"
                      danger
                      size="large"
                      onClick={() => setVisible(true)}
                  >
                    참가하기
                  </Button>
                  <CollectionCreateForm
                      visible={visible}
                      onCreate={onCreate}
                      onCancel={() => {
                        setVisible(false);
                      }}
                  />
                </>
            ) : (
                ""
            )}
          </Col>
        </Row>
      </>
  );
};

export default MeetJoin;
