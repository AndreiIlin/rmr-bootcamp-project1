import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import { useAppDispatch } from '../../../../hooks/defaultHooks';
import {
  Report,
  ReportType,
  useChangeReportMutation,
  useCreateReportMutation,
} from '../../../../store/api/reportsApiSlice/reportsApiSlice';
import { closeModal } from '../../../../store/slices/modalSlice';
import { isFetchBaseQueryError } from '../../../../utils/helpers';

export const useSubmitForm = (isEditing: boolean, type: ReportType) => {
  const [createReport] = useCreateReportMutation();
  const [changeReport] = useChangeReportMutation();
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const handleClose = () => {
    dispatch(closeModal());
  };
  return isEditing
    ? async (report: Report) => {
        try {
          await changeReport(report).unwrap();
          handleClose();
          toast.success(t('toast.successChangeReport'));
        } catch (error) {
          if (isFetchBaseQueryError(error)) {
            console.log(error);
          }
        }
      }
    : async (report: Report) => {
        try {
          await createReport({ report, type }).unwrap();
          handleClose();
          toast.success(t('toast.successSendReport'));
        } catch (error) {
          if (isFetchBaseQueryError(error)) {
            console.log(error);
          }
        }
      };
};

export default useSubmitForm;
