/* eslint-disable camelcase */
import React, { FC } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import { useFormik } from 'formik';
import { toast } from 'react-toastify';
import { useReplenishmentMoneyMutation } from '../../../store/api/moneyApiSlice/moneyApiSlice';
import { isFetchBaseQueryError } from '../../../utils/helpers';
import { useAppDispatch, useAppSelector } from '../../../hooks/defaultHooks';
import { modalExtra } from '../../../selectors/selectors';
import { closeModal } from '../../../store/slices/modalSlice';

const UserReplenishment: FC = () => {
  const user_id = useAppSelector(modalExtra) as unknown as string;
  const dispatch = useAppDispatch();
  const [replenishment] = useReplenishmentMoneyMutation();
  const initialValues = {
    count: 0,
  };
  const formik = useFormik({
    initialValues,
    validateOnChange: false,
    onSubmit: async (values) => {
      try {
        await replenishment({ userId: user_id, amount: values.count }).unwrap();
        dispatch(closeModal());
        toast.success('Транзакция выполнена');
      } catch (error) {
        if (isFetchBaseQueryError(error)) console.log(error);
      }
    },
  });
  return (
    <>
      <Modal.Header closeButton>Пополнить Счет</Modal.Header>
      <Modal.Body>
        <Form onSubmit={formik.handleSubmit}>
          <Form.Control
            type="number"
            id="count"
            name="count"
            value={formik.values.count}
            onChange={formik.handleChange}
          />
          <Button type="submit" className="my-4">
            Пополнить
          </Button>
        </Form>
      </Modal.Body>
    </>
  );
};

export default UserReplenishment;
