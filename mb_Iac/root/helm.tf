resource "helm_release" "alb-ingress-controller"{
  depends_on = [module.eks, module.vpc, helm_release.cert-manager]
  repository = "https://aws.github.io/eks-charts"
  name = "aws-load-balancer-controller"
  chart = "aws-load-balancer-controller"
  version = "1.8.2"
  namespace = "kube-system"

  timeout = 600

  set {
    name  = "clusterName"
    value = module.eks.mb_eks_cluster_name
  }
  set {
    name  = "region"
    value = "ap-northeast-2"
  }
  set {
    name  = "vpcId"
    value = module.vpc.vpc_id
  }
  set {
    name  = "rbac.create"
    value = "true" #if true, create and use RBAC resource
  }
  set {
    name  = "serviceAccount.create"
    value = "false"
  }
  set {
    name  = "serviceAccount.name"
    value = "aws-load-balancer-controller"
  }
  set {
    name  = "createIngressClassResource"
    value = "true"
  }
  set {
    name  = "crds.enabled"
    value = "true"
  }
}

resource "helm_release" "cert-manager"{
  repository = "https://charts.jetstack.io"
  name = "jetpack"
  chart = "cert-manager"

  namespace  = "cert-manager"
  create_namespace = true      # 네임스페이스가 없는 경우 생성

  set {
    name  = "installCRDs"
    value = "true"  # Cert Manager 설치 시 CRDs도 함께 설치
  }

}