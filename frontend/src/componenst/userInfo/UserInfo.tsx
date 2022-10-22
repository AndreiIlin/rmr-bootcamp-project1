import React, { FC } from 'react';
import { Row, Col, Image } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { User } from '../../models/services/user';
const link =
  'https://static.vecteezy.com/system/resources/previews/000/377/927/original/block-user-vector-icon.jpg';
interface UserInfoProps {
  data: User | undefined;
}

const UserInfo: FC<UserInfoProps> = ({ data }) => {
  const { t } = useTranslation();
  return (
    <Row className="flex-nowrap border-bottom border-2 border-light">
      <Col xs={5} sm={4}>
        <Image className="img-fluid shadow mb-4" src={link} alt="user" roundedCircle />
      </Col>
      <Col xs={7} sm={8}>
        <h2>{t('profile.userInfo')}</h2>
        <p>
          {t('profile.email')}: {data?.email}
        </p>
      </Col>
    </Row>
  );
};

export default UserInfo;
