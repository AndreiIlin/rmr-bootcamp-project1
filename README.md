# Репозиторий проекта "TrueStore"


## Порядок работы команды в системе Git

1. Разработка ведется в ветке <code>develop</code>, добавление нового функционала осуществляется через Pull Request
 в ветку <code>develop</code> из соответствющей feature-ветки.
2. В <code>develop</code> ветку сливается только полностью работоспособный код.
3. Feature ветка должна иметь осмысленное и краткое название, например:
  - <code>feature/some_name</code> - наименование фичи;
  - <code>bug/some_name</code> - багфикс или эксперимент;
  - <code>hotfix/some_name</code> - хотфикс.
4. Pull Request в <code>develop</code> должен отвечать требованиям атомарности и содержать:
  - заявленный функционал;
  - осмысленный заголовок и желательно краткое описание;
  - необходимые тесты для проверки функционала (как правило, юниит тесты и базовые интеграционные тесты);
  - справочную информацию и информацию по развертыванию функционала.
5. Pull Request в <code>develop</code> ветку должен:
  - не нарушать работу ранее добавленного функционала (проходятся все предыдущие тесты);
  - не объединять несколько разноплановых фич (одна фича -- один PR);
  - быть актуальным на момент создания (например, пока вы писали фичу ветка <code>develop</code> уже убежала вперед, 
 был добавлен некоторый функционал. Ответственность создателя PR -- сделать merge или rebase от <code>develop</code> ветки и 
 самостоятельно разрешить все конфликты слияния до отправки PR).
6. За слияние в <code>develop</code> ветку отвечает техлид, перед слиянием:
  - проводится code review одним из членов команды, получается подтверждение;
  - проверяется работоспособность функционала системы (автоматизированно или в ручном режиме);
  - о проведенном слиянии информируются участники команды (автоматизированно или в ручном режиме).
7. Слияние <code>develop</code> в <code>main</code> ветку означает релиз на stage стенд, проводится техлидом.
