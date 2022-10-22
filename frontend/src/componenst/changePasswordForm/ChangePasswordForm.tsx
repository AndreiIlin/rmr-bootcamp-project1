import React, { FC, useState, useRef } from 'react';
import { useFormik } from 'formik';
import { Form, Button } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import { isFetchBaseQueryError } from '../../utils/helpers';
import { useChangePasswordMutation } from '../../store/api/userInfoApiSlice/userInfoApiSlice';

const ChangePasswordForm: FC = () => {
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
      } catch (error) {
        if (isFetchBaseQueryError(error)) {
          if (error.status === 400) {
            setPasswordError(true);
            passwordRef.current?.select();
          }
        }
      } finally {
        setDisabled(false);
        setErrors({});
      }
    },
  });
  return (
    <>
      <h2 className="mt-5">{t('profile.passwordChanging')}</h2>
      <Form className="d-flex flex-wrap mb-5 gap-3" onSubmit={formik.handleSubmit}>
        <Form.Group className="position-relative col-5">
          <Form.Label>{t('profile.oldPassword')}:</Form.Label>
          <Form.Control
            type="password"
            name="oldPassword"
            placeholder={t('profile.oldPasswordPlaceholder')}
            value={formik.values.oldPassword}
            onChange={(event) => {
              formik.handleChange(event);
              formik.setErrors({});
            }}
            isInvalid={formik.touched.oldPassword && !!formik.errors.oldPassword}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.oldPassword}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="position-relative col-5">
          <Form.Label>{t('profile.newPassword')}:</Form.Label>
          <Form.Control
            type="password"
            name="newPassword"
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
          <Form.Control.Feedback type="invalid" tooltip>
            {passwordError ? t('profile.differentPassword') : formik.errors.newPassword}
          </Form.Control.Feedback>
        </Form.Group>
        <Button variant="outline-light" disabled={disabled} type="submit">
          {t('profile.changePassword')}
        </Button>
      </Form>
    </>
  );
};

export default ChangePasswordForm;
