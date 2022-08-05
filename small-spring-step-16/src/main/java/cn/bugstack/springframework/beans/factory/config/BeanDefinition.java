package cn.bugstack.springframework.beans.factory.config;

import cn.bugstack.springframework.beans.PropertyValues;


public class BeanDefinition {

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanDefinition that = (BeanDefinition) o;

        if (singleton != that.singleton) return false;
        if (prototype != that.prototype) return false;
        if (SCOPE_SINGLETON != null ? !SCOPE_SINGLETON.equals(that.SCOPE_SINGLETON) : that.SCOPE_SINGLETON != null)
            return false;
        if (SCOPE_PROTOTYPE != null ? !SCOPE_PROTOTYPE.equals(that.SCOPE_PROTOTYPE) : that.SCOPE_PROTOTYPE != null)
            return false;
        if (beanClass != null ? !beanClass.equals(that.beanClass) : that.beanClass != null) return false;
        if (propertyValues != null ? !propertyValues.equals(that.propertyValues) : that.propertyValues != null)
            return false;
        if (initMethodName != null ? !initMethodName.equals(that.initMethodName) : that.initMethodName != null)
            return false;
        if (destroyMethodName != null ? !destroyMethodName.equals(that.destroyMethodName) : that.destroyMethodName != null)
            return false;
        return scope != null ? scope.equals(that.scope) : that.scope == null;
    }

    @Override
    public int hashCode() {
        int result = SCOPE_SINGLETON != null ? SCOPE_SINGLETON.hashCode() : 0;
        result = 31 * result + (SCOPE_PROTOTYPE != null ? SCOPE_PROTOTYPE.hashCode() : 0);
        result = 31 * result + (beanClass != null ? beanClass.hashCode() : 0);
        result = 31 * result + (propertyValues != null ? propertyValues.hashCode() : 0);
        result = 31 * result + (initMethodName != null ? initMethodName.hashCode() : 0);
        result = 31 * result + (destroyMethodName != null ? destroyMethodName.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        result = 31 * result + (singleton ? 1 : 0);
        result = 31 * result + (prototype ? 1 : 0);
        return result;
    }

    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
