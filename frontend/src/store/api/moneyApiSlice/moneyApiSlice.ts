import { routes } from './../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

interface MoneyTransition {
  id?: string;
  userId: string;
  amount: number;
  created?: number;
  typeTransition?: string;
}

const moneyApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    withdrawMoney: build.mutation<MoneyTransition, MoneyTransition>({
      query: ({ userId, amount }) => ({
        url: routes.api.withdrawalMoney(),
        method: 'POST',
        body: { userId, amount },
      }),
    }),
    replenishmentMoney: build.mutation<MoneyTransition, MoneyTransition>({
      query: ({ userId, amount }) => ({
        url: routes.api.replenishmentMoney(),
        method: 'POST',
        body: { userId, amount },
      }),
    }),
    getTransitions: build.query<MoneyTransition[], void>({
      query: () => routes.api.userTransitions(),
    }),
    getBalance: build.query<number, void>({
      query: () => routes.api.userBalance(),
    }),
  }),
});

export const {
  useGetBalanceQuery,
  useGetTransitionsQuery,
  useReplenishmentMoneyMutation,
  useWithdrawMoneyMutation,
} = moneyApiSlice;
