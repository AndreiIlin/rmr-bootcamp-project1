import { routes } from '../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

export interface Report {
  id?: string;
  contractId?: string;
  title: string;
  description?: string;
  reportType?: string;
  reportStatus?: string;
  created?: string;
}

interface ReportRequest {
  report: Report;
  type: ReportType;
}

export type ReportType = 'feature' | 'bug' | 'claim';

const reportsApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    createReport: build.mutation<Report, ReportRequest>({
      query: ({ report, type }) => ({
        url: routes.api.reportsWithParam(type),
        method: 'POST',
        body: report,
      }),
      invalidatesTags: ['Report'],
    }),
    changeReport: build.mutation<Report, Report>({
      query: (report) => ({
        url: routes.api.reports(),
        method: 'PATCH',
        body: report,
      }),
      invalidatesTags: ['Report'],
    }),
    getAppReports: build.query<Report[], string>({
      query: (appId) => routes.api.reportsByApp(appId),
      providesTags: ['Report'],
    }),
    getContractReports: build.query<Report[], string>({
      query: (contractId) => routes.api.reportsByContractId(contractId),
      providesTags: ['Report'],
    }),
    getUserReports: build.query<Report[], void>({
      query: () => routes.api.userReports(),
      providesTags: ['Report'],
    }),
    getCurrentReports: build.query<Report, string>({
      query: (reportId) => routes.api.reportsWithParam(reportId),
      providesTags: ['Report'],
    }),
    approveReport: build.mutation<Report, string>({
      query: (reportId) => ({
        url: routes.api.approveReport(reportId),
        method: 'PATCH',
      }),
      invalidatesTags: ['Report'],
    }),
    rejectReport: build.mutation<Report, string>({
      query: (reportId) => ({
        url: routes.api.rejectReport(reportId),
        method: 'PATCH',
      }),
      invalidatesTags: ['Report'],
    }),
  }),
});

export const {
  useCreateReportMutation,
  useGetAppReportsQuery,
  useGetContractReportsQuery,
  useChangeReportMutation,
  useGetCurrentReportsQuery,
  useGetUserReportsQuery,
  useApproveReportMutation,
  useRejectReportMutation,
} = reportsApiSlice;
