import { routes } from './../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';
import { Contract } from '../../../models/services/contract';

interface ContractRequest {
  appId: string;
}

const contractApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    getContractById: build.query<Contract, string>({
      query: (id) => routes.api.contract(id),
      providesTags: ['Contract'],
    }),
    getMyContracts: build.query({
      query: () => routes.api.myContract(),
      providesTags: ['Contract'],
    }),
    setContract: build.mutation<Contract, ContractRequest>({
      query: (contractData) => ({
        url: routes.api.contracts(),
        method: 'POST',
        body: contractData,
      }),
      invalidatesTags: ['Contract'],
    }),
  }),
});

export const { useGetContractByIdQuery, useGetMyContractsQuery, useSetContractMutation } =
  contractApiSlice;
