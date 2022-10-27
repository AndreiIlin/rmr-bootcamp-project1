import React, { FC } from 'react';
import { Container } from 'react-bootstrap';
import ChangePasswordForm from '../../componenst/changePasswordForm';
import UserBill from '../../componenst/userBill';
import UserInfo from '../../componenst/userInfo';
import { useGetUserInfoQuery } from '../../store/api/userInfoApiSlice/userInfoApiSlice';

const ProfilePage: FC = () => {
  const { data } = useGetUserInfoQuery();
  return (
    <Container className="wrapper text-black my-5 px-4 py-3 shadow shadow-lg rounded-3">
      <UserInfo data={data} />
      <UserBill />
      <ChangePasswordForm />
    </Container>
  );
};

export default ProfilePage;
