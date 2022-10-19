import { useFormik } from 'formik';
import React, { FC, useRef, useState } from 'react';
import { Button, Col, Container, Form, Image, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import {
  useChangePasswordMutation,
  useGetUserInfoQuery,
} from '../../store/api/userInfoApiSlice/userInfoApiSlice';

const link =
  'https://static.vecteezy.com/system/resources/previews/000/377/927/original/block-user-vector-icon.jpg';

const ProfilePage: FC = () => {
  const { data } = useGetUserInfoQuery();
  const { t } = useTranslation();
  const [disabled, setDisabled] = useState<boolean>(false);
  const [changePassword] = useChangePasswordMutation();
  const passwordRef = useRef<HTMLInputElement | null>(null);
  const [passwordError, setPasswordError] = useState<boolean>(false);
  const notify = () => toast.success(t('toast.changePasswordSuccess'));
  const validationSchema = yup.object().shape({
    oldPassword: yup
      .string()
      .min(8, t('formErrors.minPasswordLength'))
      .max(30, t('formErrors.maxPasswordLength'))
      .required(t('formErrors.required')),
    newPassword: yup
      .string()
      .min(8, t('formErrors.minPasswordLength'))
      .max(30, t('formErrors.maxPasswordLength'))
      .required(t('formErrors.required')),
  });
  const formik = useFormik({
    initialValues: {
      oldPassword: '',
      newPassword: '',
    },
    validationSchema,
    validateOnChange: false,
    onSubmit: async (values, { resetForm, setErrors }) => {
      try {
        setDisabled(true);
        const response = await changePassword(values).unwrap();
        localStorage.setItem('trueStore', JSON.stringify(response));
        notify();
        resetForm();
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
      } catch (err: any) {
        if (err?.status === 400) {
          setPasswordError(true);
          passwordRef.current?.select();
        }
      } finally {
        setDisabled(false);
        setErrors({});
      }
    },
  });

  return (
    <Container className='app-container bg-light my-5 p-5 shadow shadow-lg rounded-3'>
      <Row className='flex-nowrap border-bottom border-2 border-dark'>
        <Col xs={5} sm={4}>
          <Image className='img-fluid shadow mb-4' src={link} alt='user' roundedCircle />
        </Col>
        <Col xs={7} sm={8}>
          <h2>{t('profile.userInfo')}</h2>
          <p>
            {t('profile.email')}: {data?.email}
          </p>
        </Col>
      </Row>
      <Row className='mt-5 border-bottom border-2 border-dark'>
        <Col>
          <h2>{t('profile.personalBilling')}</h2>
          <p>
            {t('profile.bill')}: {t('app.cost', { count: 500 })}
          </p>
          <Button className='mb-5'>{t('profile.replenishment')}</Button>
        </Col>
      </Row>
      <h2 className='mt-5'>{t('profile.passwordChanging')}</h2>
      <Form className='d-flex flex-wrap mb-5 gap-3' onSubmit={formik.handleSubmit}>
        <Form.Group className='position-relative col-5'>
          <Form.Label>{t('profile.oldPassword')}:</Form.Label>
          <Form.Control
            type='password'
            name='oldPassword'
            placeholder={t('profile.oldPasswordPlaceholder')}
            value={formik.values.oldPassword}
            onChange={(event) => {
              formik.handleChange(event);
              formik.setErrors({});
            }}
            isInvalid={formik.touched.oldPassword && !!formik.errors.oldPassword}
          />
          <Form.Control.Feedback type='invalid' tooltip>
            {formik.errors.oldPassword}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className='position-relative col-5'>
          <Form.Label>{t('profile.newPassword')}:</Form.Label>
          <Form.Control
            type='password'
            name='newPassword'
            placeholder={t('profile.newPasswordPlaceholder')}
            value={formik.values.newPassword}
            onChange={(event) => {
              formik.handleChange(event);
              formik.setErrors({});
              setPasswordError(false);
            }}
            isInvalid={(formik.touched.newPassword && !!formik.errors.newPassword) || passwordError}
            ref={passwordRef}
          />
          <Form.Control.Feedback type='invalid' tooltip>
            {passwordError ? t('profile.differentPassword') : formik.errors.newPassword}
          </Form.Control.Feedback>
        </Form.Group>
        <Button disabled={disabled} type='submit'>
          {t('profile.changePassword')}
        </Button>
      </Form>
    </Container>
  );
};

export default ProfilePage;
