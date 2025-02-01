data "aws_eks_cluster_auth" "example" {
    name = module.eks.mb_eks_cluster_name
  depends_on = [module.eks]
}