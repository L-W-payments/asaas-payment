package com.miniasaaslw.repository

import com.miniasaaslw.utils.StringUtils

import grails.gorm.PagedResultList
import grails.orm.HibernateCriteriaBuilder

import org.grails.datastore.mapping.query.api.BuildableCriteria

import org.hibernate.LockMode
import org.hibernate.criterion.CriteriaSpecification

@SuppressWarnings(["UnusedMethodParameter"])
trait Repository<DomainClass, RepositoryClass extends Repository> {

    Closure criteria = {}

    List<String> columnList = []

    Map search = [:]

    List<Map> defaultSortingList = [[column: "id", order: "desc"]]

    List<Map> sortingList = []

    Boolean disableDefaultSorting = false

    Boolean domainHasSoftDelete = true

    public static RepositoryClass query(Map search = [:]) {
        Repository repositoryInstance = this.newInstance()
        repositoryInstance.search = search.clone()
        repositoryInstance.validateAllowedFilters()
        repositoryInstance.addDefaultCriteria()
        repositoryInstance.buildCriteria()

        return (RepositoryClass) repositoryInstance
    }

    public static DomainClass get(Serializable id) {
        return (DomainClass) query([id: id]).disableSort().get()
    }

    public static DomainClass read(Serializable id) {
        return (DomainClass) query([id: id]).readOnly().disableSort().get()
    }

    public static DomainClass lock(Serializable id) {
        return (DomainClass) query([id: id]).lock().disableSort().get()
    }

    public <T> T get() {
        return (T) addCriteria { setMaxResults(1) }
                .getBuildableCriteria()
                .get(criteria)
    }

    public <T> PagedResultList<T> list(Map params = [:]) {
        if (params.containsKey("readOnly")) throw new RuntimeException("Para utilizar readOnly, utilize o método `readOnly()` ao invés do parâmetro")
        return (PagedResultList<T>) getBuildableCriteria().list(params, criteria)
    }

    public Integer count() {
        return (Integer) disableSort()
                .addCriteria { projections { count() } }
                .get() ?: 0
    }

    public Integer countDistinct(String fieldName) {
        return (Integer) disableSort()
                .addCriteria { projections { countDistinct(fieldName) } }
                .get() ?: 0
    }

    public Boolean exists() {
        return column("id")
                .disableSort()
                .get()
                .asBoolean()
    }

    public <T> T sum(String fieldName) {
        return (T) disableSort()
                .addCriteria { projections { sum(fieldName) } }
                .get()
    }

    public <T> T sumAbsolute(String fieldName) {
        String databaseColumnName = StringUtils.camelCaseToSnakeCase(fieldName)
        String aliasName = "sum${fieldName.capitalize()}"

        return (T) (
                disableSort()
                        .addCriteria {
                            projections {
                                sqlProjection("sum(abs(${databaseColumnName})) as ${aliasName}", [aliasName], [HibernateCriteriaBuilder.BIG_DECIMAL])
                            }
                        }
                        .get() ?: 0
        )
    }

    public RepositoryClass addCriteria(@DelegatesTo(HibernateCriteriaBuilder) Closure criteria) {
        this.criteria = this.criteria << criteria
        return (RepositoryClass) this
    }

    public void validateAllowedFilters() {
        final List<String> allowedFilterList = listAllowedFilters() + listDefaultFilters()
        search.keySet().each { filter ->
            if (!allowedFilterList.contains(filter)) {
                throw new RuntimeException("O filtro [${filter}] não é permitido no repositório [${this.class}]")
            }
        }
    }

    public void addDefaultCriteria() {
        addCriteria {
            if (getDomainHasSoftDelete()) {
                if (Boolean.valueOf(search.deletedOnly.toString())) {
                    eq("deleted", true)
                } else if (!Boolean.valueOf(search.includeDeleted.toString())) {
                    eq("deleted", false)
                }
            }

            Boolean mustApplyDefaultSorting = !getDisableDefaultSorting() && !this.sortingList
            if (mustApplyDefaultSorting) this.sortingList = getDefaultSortingList()

            for (Map sorting : this.sortingList) {
                order(sorting.column.toString(), sorting.order.toString().toLowerCase())
            }

            if (search.containsKey("id")) {
                eq("id", Long.valueOf(search.id.toString()))
            }
        }
    }

    public RepositoryClass column(String fieldName) {
        if (this.columnList) throw new RuntimeException("O método `column` deve ser chamado apenas uma vez.")
        this.columnList.addAll(fieldName)

        return addCriteria {
            projections { property(fieldName) }
        }
    }

    public RepositoryClass column(List<String> fieldNameList) {
        if (this.columnList) throw new RuntimeException("O método `column` deve ser chamado apenas uma vez.")
        this.columnList.addAll(fieldNameList)

        return addCriteria {
            projections {
                for (String fieldName : fieldNameList) {
                    property(fieldName, fieldName)
                }
            }

            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
        }
    }

    public RepositoryClass disableSort() {
        this.disableDefaultSorting = true
        this.sortingList = null
        return (RepositoryClass) this
    }

    public RepositoryClass sort(String column, String order) {
        if (!column || !order) return (RepositoryClass) this

        this.sortingList.add([column: column, order: order])
        return (RepositoryClass) this
    }

    public RepositoryClass sort(List<Map> sortingList) {
        for (Map sorting : sortingList) {
            String column = sorting.column?.toString()
            String order = sorting.order?.toString()

            sort(column, order)
        }

        return (RepositoryClass) this
    }

    public RepositoryClass distinct(String fieldName) {
        return addCriteria {
            projections { distinct(fieldName) }
        }
    }

    public RepositoryClass distinct(List<String> fieldNameList) {
        return addCriteria {
            projections { distinct(fieldNameList) }
        }
    }

    public RepositoryClass readOnly() {
        return addCriteria { setReadOnly(true) }
    }

    public RepositoryClass lock() {
        return addCriteria { setLockMode(LockMode.PESSIMISTIC_WRITE) }
    }

    public abstract void buildCriteria()

    public abstract BuildableCriteria getBuildableCriteria()

    public abstract List<String> listAllowedFilters()

    private List<String> listDefaultFilters() {
        return [
                "deletedOnly",
                "id",
                "includeDeleted"
        ]
    }
}
