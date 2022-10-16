import { useFormik } from 'formik';
import React, { FC, useRef, useState } from 'react';
import { Button, Container, Form, Image, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import {
  useChangePasswordMutation,
  useGetUserInfoQuery,
} from '../../../store/api/userInfoApiSlice/userInfoApiSlice';

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
    <Container className='p-0'>
      <Row className='flex-row flex-nowrap border-bottom border-2 border-dark'>
        <Image
          className='col-2'
          style={{ height: '300px', width: '300px' }}
          src={link}
          alt='User'
          roundedCircle
        />
        <Container className='col d-flex flex-column justify-content-center align-items-start'>
          <h2>{t('profile.userInfo')}</h2>
          <p>{t('profile.username')}: </p>
          <p>
            {t('profile.email')}: {data?.email}
          </p>
        </Container>
      </Row>
      <Row className='flex-column mt-5 border-bottom border-2 border-dark'>
        <h2>{t('profile.personalBilling')}</h2>
        <p>{t('profile.bill')}: 500 rubles</p>
        <Button className='col-2 mb-5'>{t('profile.replenishment')}</Button>
      </Row>
      <Row>
        <h2 className='mt-5'>{t('profile.passwordChanging')}</h2>
        <Form className='d-flex flex-column mb-5 col-4 gap-3' onSubmit={formik.handleSubmit}>
          <Form.Group className='position-relative'>
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
          <Form.Group className='position-relative'>
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
              isInvalid={
                (formik.touched.newPassword && !!formik.errors.newPassword) || passwordError
              }
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
      </Row>
    </Container>
  );
};

export default ProfilePage;
