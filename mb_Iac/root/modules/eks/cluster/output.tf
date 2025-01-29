
output "mb_eks_cluster_name" {
  value = aws_eks_cluster.mb_eks_cluster.name
}

output "oidc_url" {
  value = aws_eks_cluster.mb_eks_cluster.identity[0].oidc[0].issuer
  depends_on  = [aws_eks_cluster.mb_eks_cluster]
}

output "oidc_url_without_https" {
  value = replace(aws_eks_cluster.mb_eks_cluster.identity[0].oidc[0].issuer, "https://", "")
  depends_on  = [aws_eks_cluster.mb_eks_cluster]
}

output "endpoint" {
  value = aws_eks_cluster.mb_eks_cluster.endpoint
}

output "kubeconfig-certificate-authority-data" {
  value = aws_eks_cluster.mb_eks_cluster.certificate_authority[0].data
}