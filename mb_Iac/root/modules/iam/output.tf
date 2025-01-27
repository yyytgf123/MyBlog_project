output "eks_cluster_role" {
  value = aws_iam_role.mb_eks_cluster_role.arn
}

output "eks_workernode_role" {
  value = aws_iam_role.mb_eks_workernode_role.name
  //실패 시 arn 대신 name 사용
}

output "eks_workernode_role_arn" {
  value = aws_iam_role.mb_eks_workernode_role.arn
}
