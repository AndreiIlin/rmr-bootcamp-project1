import { routes } from './../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';
export interface Report {
  id?: string;
  contractId: string;
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
        url: routes.api.reports(type),
        method: 'POST',
        body: report,
      }),
    }),
    getAppReports: build.query<Report[], string>({
      query: (appId) => routes.api.reportsByApp(appId),
    }),
    getContractReports: build.query<Report[], string>({
      query: (contractId) => routes.api.reportsByContractId(contractId),
    }),
    getUserReports: build.query<Report[], void>({
      query: () => routes.api.userReports(),
    }),
    getCurrentReports: build.query<Report[], string>({
      query: (reportId) => routes.api.reports(reportId),
    }),
  }),
});

export const {
  useCreateReportMutation,
  useGetAppReportsQuery,
  useGetContractReportsQuery,
  useGetCurrentReportsQuery,
  useGetUserReportsQuery,
} = reportsApiSlice;
