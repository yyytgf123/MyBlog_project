provider "kubernetes" {
  config_path = "~/.kube/config"
  host                   = var.create_cluster ? module.eks.endpoint : ""
  token                  = var.create_cluster ? data.aws_eks_cluster_auth.example.token : ""
  cluster_ca_certificate = var.create_cluster ? base64decode(module.eks.kubeconfig-certificate-authority-data) : ""
}



provider "helm" {
  kubernetes {
    config_path = "~/.kube/config"
    host                   = var.create_cluster ? module.eks.endpoint : ""
    token                  = var.create_cluster ? data.aws_eks_cluster_auth.example.token : ""
    cluster_ca_certificate = var.create_cluster ? base64decode(module.eks.kubeconfig-certificate-authority-data) : ""
  }
}